package Tests.ExamenFinal;

import org.testng.annotations.Factory;

public class examenFactory {

    @Factory
    public Object[] salesforceEmailFactory() {
        return new Object[]{
                new prueba_netflix("testing@test.com"),
                new prueba_netflix("prueba@pruebaTest.com"),
        };
    }
}
