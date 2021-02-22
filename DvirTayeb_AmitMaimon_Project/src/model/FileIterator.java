package model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Map;

public class FileIterator implements Iterable<Product> {

	private RandomAccessFile raf;
	private File productFile;
	/* private long currentIndex; */
	private long currentSize;

	public FileIterator(File productFile, RandomAccessFile raf) throws IOException {
		this.productFile = productFile;
		this.raf = raf;
		currentSize = raf.length();

	}

	@Override
	public Iterator<Product> iterator() {
		
		Iterator<Product> it = null;
		try {
			it = new Iterator<Product>() {

				private long currentIndex = raf.getFilePointer();

				@Override
				public boolean hasNext() {
					return currentIndex < currentSize;
				}

				@Override
				public Product next() {
					String prodString[] = new String[7];
					try {
						for (int i = 0; i < prodString.length; i++) {
							prodString[i] = raf.readUTF();
						}
						Client client = new Client(prodString[4], prodString[5], Boolean.parseBoolean(prodString[6]));
						return new Product(prodString[0], Integer.parseInt(prodString[1]),
								Integer.parseInt(prodString[2]), client, prodString[3]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
					return null;
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}

			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return it;
	}
}
