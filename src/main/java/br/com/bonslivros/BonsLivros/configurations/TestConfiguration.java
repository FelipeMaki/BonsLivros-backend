package br.com.bonslivros.BonsLivros.configurations;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.bonslivros.BonsLivros.services.DBService;

@Configuration
@Profile("teste")
public class TestConfiguration {
  @Autowired
  private DBService dbService;

  private boolean instance() throws ParseException {
    this.dbService.implementDB();
    return true;
  }
}
