package pl.polskieligi.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import pl.polskieligi.log.ImportProjectLogic;

public class ProjectProcessor implements ItemProcessor<Integer, Object> {

	@Autowired
	ImportProjectLogic importProjectLogic;
	
	public Object process(Integer p) throws Exception {
		String result = importProjectLogic.doImport(p);		
		System.out.println(result);
		return null;
	}
}
