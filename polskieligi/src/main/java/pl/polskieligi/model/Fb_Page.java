package pl.polskieligi.model;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fb_Page {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private BigInteger page_id;
	private Integer team_id;

	public Fb_Page() {
		page_id = new BigInteger("0");
		team_id = 0;
	}

	public Long getId() {
		return id;
	}

	public BigInteger getPage_id() {
		return page_id;
	}

	public void setPage_id(BigInteger page_id) {
		this.page_id = page_id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}	
}
