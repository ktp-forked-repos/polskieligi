package pl.polskieligi.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.polskieligi.model.Project;
import pl.polskieligi.model.Team;

@Repository
@Transactional
public class ProjectDAOImpl extends AbstractDAOImpl<Project> implements ProjectDAO {
	public ProjectDAOImpl() {
		super(Project.class);
	}

	public Project retrieveProjectByMinut(Integer minutId) {
		Project result = null;
		Session session = getCurrentSession();

		Query query = session.createQuery("from Project where minut_id = :minut_id");
		query.setParameter("minut_id", minutId);
		@SuppressWarnings("unchecked")
		List<Project> projects = query.list();
		for (Project p : projects) {
			result = p;
		}

		return result;
	}

	public Long saveUpdate(Project leagueProject) {
		Long result = null;
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"from Project where minut_id = :minut_id or (league_id = :league_id and season_id = :season_id)");
		query.setParameter("minut_id", leagueProject.getMinut_id());
		query.setParameter("league_id", leagueProject.getLeague_id());
		query.setParameter("season_id", leagueProject.getSeason_id());
		Project oldProject = null;
		@SuppressWarnings("unchecked")
		List<Project> leagues = query.list();
		for (Project l : leagues) {
			oldProject = l;
			if (leagueProject.getMinut_id() > 0) {
				oldProject.setMinut_id(leagueProject.getMinut_id());
			}
			if (leagueProject.getLeague_id() > 0) {
				oldProject.setLeague_id(leagueProject.getLeague_id());
			}
			if (leagueProject.getSeason_id() > 0) {
				oldProject.setSeason_id(leagueProject.getSeason_id());
			}
			if (leagueProject.getName() != null && !leagueProject.getName().isEmpty()) {
				oldProject.setName(leagueProject.getName());
			}
			if (leagueProject.getStart_date() != null && !leagueProject.getStart_date().equals(new Date(0))) {
				oldProject.setStart_date(leagueProject.getStart_date());
			}
			oldProject.setArchive(leagueProject.getArchive());
			if (leagueProject.getType() > 0) {
				oldProject.setType(leagueProject.getType());
			}

			session.update(oldProject);
			result = oldProject.getId();
		}
		if (oldProject == null) {
			result = (Long) session.save(leagueProject);
		}

		return result;
	}

	public Project getLastProjectForTeam(Integer teamId) {
		Project result = null;
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT p.* " + "FROM team_leagues tl "
				+ "LEFT JOIN projects p ON p.id = tl.project_id "
				+ "WHERE tl.team_id = :team_id and p.published = :published and p.type = :type AND p.start_date = ( "
				+ "SELECT MAX( p2.start_date ) " + "FROM team_leagues tl2 "
				+ "LEFT JOIN projects p2 ON p2.id = tl2.project_id "
				+ "WHERE tl2.team_id = tl.team_id and p2.published = :published and p2.type = :type )");
		query.addEntity(Project.class);
		query.setParameter("team_id", teamId);
		query.setParameter("published", true);
		query.setParameter("type", Project.REGULAR_LEAGUE);
		for (Object i : query.list()) {
			result = (Project) i;
		}

		return result;
	}
	
	public Long getOpenProjectsCount() {
		Session session = getCurrentSession();
		return (Long) session.createCriteria(Project.class).setProjection(Projections.rowCount()).uniqueResult();
	}
}
