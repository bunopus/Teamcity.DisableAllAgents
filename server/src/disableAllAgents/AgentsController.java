package disableAllAgents;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AgentsController extends BaseController {

  @NotNull
  private final BuildAgentManager myBuildAgentManager;

  public AgentsController(@NotNull SBuildServer server,
                          @NotNull WebControllerManager manager) {
    super(server);
    myBuildAgentManager = server.getBuildAgentManager();
    manager.registerController(Constants.WEB.AGENTS_ACTIONS_URL, this);
  }

  @Nullable
  @Override
  protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try
    {
      final SUser user = (SUser) request.getUserPrincipal();
      boolean agentsState = Boolean.parseBoolean(request.getParameter(Constants.WEB.AGENTS_STATUS_PARAMETER_NAME));
      List<SBuildAgent> agents =  myBuildAgentManager.getRegisteredAgents();
      for (SBuildAgent agent : agents) {
        agent.setEnabled(agentsState, user, String.format(Constants.LITERALS.AGENTS_STATUS_CHANGE_REASON, user.getName()));
      }
    }
    catch (Exception e)
    {
      //TODO Exception handling
    }

    return null;
  }
}
