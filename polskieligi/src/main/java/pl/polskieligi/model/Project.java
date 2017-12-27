package pl.polskieligi.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {
	public final static Integer REGULAR_LEAGUE = 1;
	public final static Integer OTHER = 2;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Integer minut_id;
	private String name;
	private Long league_id;
	private Long season_id;
	private Date start_date;
	private Boolean published;
	private Boolean archive;
	private Integer type;

	public Project() {
		minut_id = 0;
		name = "";
		league_id = Long.valueOf(0);
		season_id = Long.valueOf(0);
		start_date = new Date(0);
		published = false;
		archive = false;
		type = 0;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMinut_id() {
		return minut_id;
	}

	public void setMinut_id(Integer minut_id) {
		this.minut_id = minut_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLeague_id() {
		return league_id;
	}

	public void setLeague_id(Long league_id) {
		this.league_id = league_id;
	}

	public Long getSeason_id() {
		return season_id;
	}

	public void setSeason_id(Long season_id) {
		this.season_id = season_id;
	}

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return name;
	}
}
