package model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

public class FileIterator implements Iterator<Product> {

	private RandomAccessFile raf;
	private long currentSize;
	private long currentIndex;
	private long lastIndex;
	private long prevIndex;

	public FileIterator(RandomAccessFile raf) throws IOException {
		this.raf = raf;
		if (this.raf != null) {
			currentSize = this.raf.length();
			currentIndex = 0;
			lastIndex = -1;
			prevIndex = 0;
		}
	}

	@Override
	public boolean hasNext() {
		return currentIndex < currentSize;
	}

	@Override
	public Product next() {
		String productString[] = new String[7];
		try {
			raf.seek(currentIndex);
			prevIndex = currentIndex;
			for (int i = 0; i < productString.length; i++) {
				int num = raf.read();
				String lenNum = "" + num;
				byte[] data = new byte[num];
				raf.read(data);
				productString[i] = new String(data);
				currentIndex += productString[i].length() + lenNum.length();
			}
			Client client = new Client(productString[4], productString[5], Boolean.parseBoolean(productString[6]));
			return new Product(productString[0], Integer.parseInt(productString[1]), Integer.parseInt(productString[2]),
					client, productString[3]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove() {
		try {
			byte[] restData = new byte[(int) (raf.length() - raf.getFilePointer())];
			raf.read(restData);
			String restProducts = new String(restData);
			long newSize = prevIndex + restProducts.length();
			raf.seek(prevIndex);
			raf.setLength(newSize);
			currentSize = raf.length();
			raf.write(restProducts.getBytes());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
