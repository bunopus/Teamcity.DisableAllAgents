package disableAllAgents;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildServer;
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

//    TODO Add username to message
//    Looks like we cannot obtain username here.
//    Maybe because http://stackoverflow.com/questions/28147261/request-getuserprincipal-is-null-in-requestlistener-despite-that-authenticatio
    boolean agentsState = Boolean.parseBoolean(request.getParameter(Constants.WEB.AGENTS_STATUS_PARAMETER_NAME));
    List<SBuildAgent> agents = myBuildAgentManager.getRegisteredAgents();
    for (SBuildAgent agent : agents) {
      try {
        agent.setEnabled(agentsState, null, Constants.LITERALS.AGENTS_STATUS_CHANGE_REASON);
      } catch (Exception e) {
        Loggers.SERVER.warn(e.getMessage());
        Loggers.SERVER.debug(e);
      }
    }
    return null;
  }
}
