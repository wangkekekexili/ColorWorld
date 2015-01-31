package colorworld.utilities;

public class FileHelperTest {
	public static void main(String[] args) {
		System.out.println(FileHelper.getExtension("hello.txt"));
		System.out.println(FileHelper.getExtension("hello"));
		System.out.println(FileHelper.getExtension("Image1.jpg"));
		System.out.println(FileHelper.getExtension("hello."));

		System.out.println(FileHelper.rename("world", "hello"));
		System.out.println(FileHelper.rename("download/world", "hello.jpg"));
		System.out.println(FileHelper.rename("src/test/world", "hello"));

	}
}
