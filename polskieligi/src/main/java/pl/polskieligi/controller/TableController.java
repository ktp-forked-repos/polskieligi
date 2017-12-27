package pl.polskieligi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.polskieligi.dao.TableDAO;

@Controller
public class TableController {
	
	@Autowired
	TableDAO tableDAO;
	
	@RequestMapping("/table")
	public ModelAndView showTable(Long projectId) {		
		List<TableRow> rows = tableDAO.getTableRows(projectId);
		ModelAndView mv = new ModelAndView("table", "rows", rows);
		return mv;		
	}
}
