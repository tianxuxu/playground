package ch.rasc.springplayground.equalsverifier;

public final class Point {
	private final int x;

	private final int y;

	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(final Object obj) {
		return true;
	}

}
