package br.com.roberth.avaliacaoTecnica;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "API REST integrante da avaliação técnica do processo de seleção da empresa DBServer",
                version = "0.0.1-alpha",
                description = "API REST para gestão de pautas de votação",
                license = @License(name = "LGPLv3", url = "https://www.gnu.org/licenses/lgpl-3.0.pt-br.html"),
                contact = @Contact(name = "Roberth Oliveira Corgosinho", email = "roberth.corgosinho@gmail.com")
        )
)
public class SwaggerConfig {
}
