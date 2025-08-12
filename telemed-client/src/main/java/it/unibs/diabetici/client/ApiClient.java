package it.unibs.diabetici.client;

import com.fasterxml.jackson.databind.*;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.*;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class ApiClient {
  private static String token; private static final ObjectMapper M = new ObjectMapper();
  private final String base;
  public ApiClient(String base){ this.base = base; }
  public static String getToken(){ return token; }

  public LoginResponse login(String email, String password) throws Exception {
    try(CloseableHttpClient http = HttpClients.createDefault()){
      HttpPost post = new HttpPost(base+"/api/auth/login");
      post.setHeader("Content-Type","application/json");
      post.setEntity(new StringEntity(M.writeValueAsString(new LoginRequest(email,password))));
      var resp = http.execute(post);
      var node = M.readTree(resp.getEntity().getContent());
      token = node.get("token").asText();
      return new LoginResponse(
        node.get("role").asText(),
        node.get("pazienteId").isNull()?null:node.get("pazienteId").asLong(),
        node.get("diabetologoId").isNull()?null:node.get("diabetologoId").asLong(),
        token);
    }
  }

  public record LoginRequest(String email, String password){}
  public record LoginResponse(String role, Long pazienteId, Long diabetologoId, String token){}
}
