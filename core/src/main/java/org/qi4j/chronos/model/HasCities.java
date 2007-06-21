package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.CityComposite;

public interface HasCities
{
    void addCity(CityComposite city);

    void removeCity( CityComposite city);

    List<CityComposite> getCities();
}