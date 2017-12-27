package pl.polskieligi.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.polskieligi.model.Match;
import pl.polskieligi.model.Team;
import pl.polskieligi.model.TeamLeague;

@Repository
@Transactional
public class TeamLeagueDAOImpl  extends AbstractDAOImpl<TeamLeague> implements TeamLeagueDAO{
	public TeamLeagueDAOImpl() {
		super(TeamLeague.class);
	}
	public Long saveUpdate(TeamLeague teamLeague) {		
		Long result = null;
		Session session = getCurrentSession();
			Query query = session
					.createQuery("from TeamLeague where project_id = :project_id AND team_id = :team_id");
			query.setParameter("project_id", teamLeague.getProject_id());
			query.setParameter("team_id", teamLeague.getTeam_id());
			TeamLeague oldTeamLeague = null;
			@SuppressWarnings("unchecked")
			List<TeamLeague> leagues = query.list();
			for (TeamLeague tl : leagues) {
				oldTeamLeague = tl;
				result = tl.getId();
			}
			if (oldTeamLeague == null) {
				result = (Long) session.save(teamLeague);
			}
			
		return result;
	}

	public void updateTeams(Long projectId,
			Collection<Long> teamIds) {

		Session session = getCurrentSession();

			Query query = session
					.createQuery("from TeamLeague where project_id = :project_id AND team_id NOT IN (:team_id)");
			query.setParameter("project_id", projectId);
			query.setParameterList("team_id", teamIds);

			@SuppressWarnings("unchecked")
			List<TeamLeague> teamsToDelete = query.list();
			for (TeamLeague tl : teamsToDelete) {							
				query = session
						.createQuery("from Match where project_id = :project_id AND (matchpart1 = :matchpart1 or matchpart2 = :matchpart2)");
				query.setParameter("project_id", projectId);
				query.setParameter("matchpart1", tl.getTeam_id());
				query.setParameter("matchpart2", tl.getTeam_id());

				@SuppressWarnings("unchecked")
				List<Match> matchesToDelete = query.list();
				for (Match m : matchesToDelete) {
					session.delete(m);
				}
				session.delete(tl);
			}
			
	}
	
	@SuppressWarnings("unchecked")
	public List<Team> getTeams(Long projectId) {
		List<Team> result = null;
		Session session = getCurrentSession();

			Query query = session.createQuery("from Team t where exists ( from TeamLeague tl where t.id = tl.team_id and tl.project_id = :project_id) order by t.name");
			query.setParameter("project_id", projectId);
			result = query.list();			
			
		return result;
	}
}
