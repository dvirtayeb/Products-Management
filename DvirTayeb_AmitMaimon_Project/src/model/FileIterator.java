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
		Iterator<Product> it;
		try {
			it = new Iterator<Product>() {
				
				private long currentIndex = raf.getFilePointer();

				@Override
				public boolean hasNext() {
					return currentIndex < currentSize;
				}

				@Override
				public Product next() {
					try {
						raf.re;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
