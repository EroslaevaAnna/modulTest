package ru.inno.course.player.data;

import ru.inno.course.player.model.Player;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

// научить работать с xml
public class DataProviderXML implements DataProvider{

    private final static Path FILEPATH = Path.of("./data.xml");
    @Override
    public void save(Collection<Player> players) throws IOException {

    }

    @Override
    public Collection<Player> load() throws IOException {
        return new ArrayList<>();
    }
}
