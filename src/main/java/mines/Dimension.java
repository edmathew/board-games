package mines;

public class Dimension {

	private int width;
	private int height;

	public Dimension(final int width, final int height) {
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException();

		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
