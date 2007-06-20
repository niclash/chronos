package org.qi4j.chronos.model;

import java.util.List;

public interface HasCities
{
    void addCity(City city);

    void removeCity(City city);

    List<City> getCities();
}
