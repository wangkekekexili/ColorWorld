package colorworld.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javafx.util.Pair;

import javax.imageio.ImageIO;

/**
 * Color Coherent Vector
 * Pass, G., Zabih, R., & Miller, J. (1997, February). Comparing images using color coherence vectors. In Proceedings of the fourth ACM international conference on Multimedia (pp. 65-73). ACM.
 * 
 * We employs ccv in RGB color space
 * 
 * @author Ke Wang
 *
 */

public class ColorCoherentVector {
	
	private int[][] coherencePair;
	private int bins;
	
	public ColorCoherentVector() {
		coherencePair = new int[8*8*8][2];
		bins = 8;
	}
	
	public ColorCoherentVector(int n) {
		coherencePair = new int[n*n*n][2];
		bins = n;
	}
	
	public boolean loadImage(String imageFilePath) {
		
		// check if the image exists
		File imageFile = new File(imageFilePath);
		if (imageFile.isFile() == false) {
			return false;
		}
		
		// if the image exists, open it and extract colors
		BufferedImage image = null;
		try{
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			return false;
		} finally {
		}
		
		
		int width = image.getWidth();
		int height = image.getHeight();
		int threshold = width*height/100;
		
		// blur the image
		ColorHelper.blur(image);
		
		int bucket[][] = new int[width][height];
		
		for (int i = 0;i != width;i++) {
			for (int j = 0;j != height;j++) {
				int colorInt =  image.getRGB(i,j);
				int r = (colorInt >> 16) & 0xFF;
				int g = (colorInt >> 8) & 0xFF;
				int b = (colorInt) & 0xFF;
				bucket[i][j] = r / (256/this.bins) * this.bins*this.bins + g / (256/this.bins) * this.bins + b / (256/this.bins);
			}
		}
		
		boolean[][] visited = new boolean[width][height];
		for (int i = 0;i != width;i++) {
			for (int j = 0;j != height;j++) {
				if (visited[i][j] == false) {
					int count = 0;
					int b = bucket[i][j]; // which bucket current pixel is in
					boolean[][] visitedThisTime = new boolean[width][height];
					Queue<Pair<Integer, Integer>> q = new LinkedList<Pair<Integer,Integer>>();
					boolean[][] inQueue = new boolean[width][height];
					Pair<Integer, Integer> newPair = new Pair<Integer, Integer>(new Integer(i), new Integer(j));
					q.add(newPair);
					while (q.isEmpty() == false) {
						Pair<Integer, Integer> currentPair = q.poll();
						int first = currentPair.getKey().intValue();
						int second = currentPair.getValue().intValue();
						visitedThisTime[first][second] = true;
						if (bucket[first][second] == b) {
							// set this pixel as visited
							visited[first][second] = true;
							// increase count
							count++;
							// get neighbor pixel that is valid and not visited
							// i-1, j-1
							if (first-1>=0 && second-1>=0 && visited[first-1][second-1]==false && visitedThisTime[first-1][second-1]==false && inQueue[first-1][second-1]==false) {
								q.add(new Pair<Integer, Integer>(new Integer(first-1), new Integer(second-1)));
								inQueue[first-1][second-1] = true;
							}
							// i-1, j
							if (first-1>=0 && visited[first-1][second]==false && visitedThisTime[first-1][second]==false && inQueue[first-1][second]==false) {
								q.add(new Pair<Integer, Integer>(new Integer(first-1), new Integer(second)));
								inQueue[first-1][second] = true;
							}
							// i-1, j+1
							if (first-1>=0 && second+1<=height-1 && visited[first-1][second+1]==false && visitedThisTime[first-1][second+1]==false && inQueue[first-1][second+1]==false) {
								q.add(new Pair<Integer, Integer>(new Integer(first-1), new Integer(second+1)));
								inQueue[first-1][second+1] = true;
							}
							// i, j-1
							if (second-1>=0 && visited[first][second-1]==false && visitedThisTime[first][second-1]==false && inQueue[first][second-1]==false) {
								q.add(new Pair<Integer, Integer>(new Integer(first), new Integer(second-1)));
								inQueue[first][second-1] = true;
							}
							// i, j+1
							if (second+1<=height-1 && visited[first][second+1]==false && visitedThisTime[first][second+1]==false && inQueue[first][second+1]==false) {
								q.add(new Pair<Integer, Integer>(new Integer(first), new Integer(second+1)));
								inQueue[first][second+1] = true;
							}
							// i+1, j-1
							if (first+1<=width-1 && second-1>=0 && visited[first+1][second-1]==false && visitedThisTime[first+1][second-1]==false && inQueue[first+1][second-1]==false) {
								q.add(new Pair<Integer, Integer>(new Integer(first+1), new Integer(second-1)));
								inQueue[first+1][second-1] = true;
							}
							// i+1, j
							if (first+1<=width-1 && visited[first+1][second]==false && visitedThisTime[first+1][second]==false && inQueue[first+1][second]==false) {
								q.add(new Pair<Integer, Integer>(new Integer(first+1), new Integer(second)));
								inQueue[first+1][second] = true;
							}
							// i+1, j+1
							if (first+1<=width-1 && second+1<=height-1 && visited[first+1][second+1]==false && visited[first+1][second+1]==false && inQueue[first+1][second+1]==false) {
								q.add(new Pair<Integer, Integer>(new Integer(first+1), new Integer(second+1)));
								inQueue[first+1][second+1] = true;
							}
						} // end if
					} // end while
					if (count >= threshold) {
						this.coherencePair[b][0] += count;
					} else {
						this.coherencePair[b][1] += count;
					}
				}
			}
		}

		return true;
	}
	
	
}
