package disableAllAgents;

import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.serverSide.auth.SecurityContext;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimplePageExtension;
import jetbrains.buildServer.web.util.WebUtil;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UiExtension extends SimplePageExtension {

  @NotNull
  private static final String EXTENSION_INCLUDE_URL = "buttons.jsp";

  @NotNull
  private static final String EXTENSION_AVAILABILITY_URL = "/agents.html";

  @NotNull
  private final SecurityContext mySecurityContext;

  public UiExtension(@NotNull final PagePlaces pagePlaces,
                     @NotNull final PluginDescriptor descriptor,
                     @NotNull final SecurityContext securityContext) {
    super(pagePlaces);
    mySecurityContext = securityContext;
    setPlaceId(PlaceId.BEFORE_CONTENT);
    setPluginName(descriptor.getPluginName());
    setIncludeUrl(descriptor.getPluginResourcesPath(EXTENSION_INCLUDE_URL));
  }

  @Override
  public boolean isAvailable(@NotNull final HttpServletRequest request) {
    final SUser user = (SUser) mySecurityContext.getAuthorityHolder().getAssociatedUser();
    return WebUtil.getPathWithoutAuthenticationType(WebUtil.getPathWithoutContext(request, WebUtil.getOriginalRequestUrl(request))).startsWith(EXTENSION_AVAILABILITY_URL)
            && user != null
            && user.isPermissionGrantedGlobally(Permission.ENABLE_DISABLE_AGENT);
  }

  @Override
  public void fillModel(@NotNull final Map<String, Object> model, @NotNull final HttpServletRequest request) {
    super.fillModel(model, request);
  }
}




