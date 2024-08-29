package ru.inno.course.player.data;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.inno.course.player.model.Player;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public class DataProviderJSON implements DataProvider{
    private final ObjectMapper mapper = new ObjectMapper();
    private final static Path FILEPATH = Path.of("./data.json");
    @Override
    public void save(Collection<Player> players) throws IOException {
        mapper.writeValue(FILEPATH.toFile(), players);
    }

    @Override
    public Collection<Player> load() throws IOException {
        return mapper.readValue(FILEPATH.toFile(), new TypeReference<Collection<Player>>(){});
    }
}
