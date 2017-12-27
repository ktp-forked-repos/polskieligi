package pl.polskieligi.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class ProjectImportReader implements ItemReader<Integer> {
	private final Integer START = 9388;
	private final Integer END = 9390;
	
	private Integer index = START;
	@Override
	public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(index<=END) {
			return index++;	
		}
		return null;
	}
}
