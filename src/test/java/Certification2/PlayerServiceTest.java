package Certification2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.inno.course.player.model.Player;
import ru.inno.course.player.service.PlayerService;
import ru.inno.course.player.service.PlayerServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

public class PlayerServiceTest {
    String NICKNAME = UUID.randomUUID().toString();
    PlayerService service;

    @BeforeEach
    public void setup() {
        service = new PlayerServiceImpl();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("./data.json"));
    }

    @Test
    @Tag("Позитивные")
    @DisplayName(value = "1. Проверка добавления игрока")
    public void testCreatePlayer() {

        int playerId = service.createPlayer(NICKNAME);
        Player playerById = service.getPlayerById(playerId);

        Assertions.assertEquals(playerId, playerById.getId());
        Assertions.assertEquals(0, playerById.getPoints());
        Assertions.assertEquals(NICKNAME, playerById.getNick());
        Assertions.assertTrue(playerById.isOnline());
    }

    @Test
    @Tag("Позитивные")
    @DisplayName("2.Проверка удаления игрока")
    public void testDeletePlayer() {

        int playerId = service.createPlayer(NICKNAME);
        service.getPlayerById(playerId);
        service.deletePlayer(playerId);
        Collection<Player> ListBefore = service.getPlayers();
        System.out.println(ListBefore.isEmpty());
    }

    @Test
    @Tag("Позитивные")
    @DisplayName(value = "2.(нет json-файла) добавить игрока ")
    public void testCreatePlayerNoJson() {

        int playerId = service.createPlayer(NICKNAME);
        Player playerById = service.getPlayerById(playerId);

        Assertions.assertEquals(playerId, playerById.getId());
        System.out.println(playerId);
    }

    @Test
    @Tag("Позитивные")
    @DisplayName(value = "56.(есть json-файла) добавить игрока ")
    public void testCreatePlayerJsonYes() {

        int playerId = service.createPlayer(NICKNAME);
        Player playerById = service.getPlayerById(playerId);

        Assertions.assertEquals(playerId, playerById.getId());
        System.out.println(playerId);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, -50, 0, 100, -5000000})
    @Tag("Позитивные")
    @DisplayName("4.начислить баллы существующему игроку")
    public void testAddPoints(int points) {

        int playerId = service.createPlayer(NICKNAME);
        service.addPoints(playerId, points);
        Player playerById = service.getPlayerById(playerId);

        Assertions.assertEquals(points, playerById.getPoints());
    }

    @ParameterizedTest
    @ArgumentsSource(PointsProvider.class)
    @Tag("Позитивные")
    @DisplayName("6.начислить дополнительные баллы существующему игроку")
    public void testAddPoints2(int pointsToAdd, int pointsToBe) {

        int playerId = service.createPlayer(NICKNAME);
        service.addPoints(playerId, pointsToBe);
        Player playerById = service.getPlayerById(playerId);
        service.addPoints(playerId, pointsToAdd);

        Assertions.assertEquals(pointsToBe + pointsToAdd, playerById.getPoints());
    }

    @Test
    @Tag("Позитивные")
    @DisplayName("7. Получить игрока по id")
    public void testGetPlayerById() {
        int playerId = service.createPlayer(NICKNAME);
        service.getPlayerById(playerId);
        System.out.println(playerId);
    }

    @Disabled
    @Test
    @Tag("Позитивный")
    @DisplayName("8.проверить корректность сохранения в файл")
    public void jshsj() {
//
    }

    @Disabled
    @Test
    @Tag("Позитивный")
    @DisplayName("9.проверить корректность загрузки json-файла")
    public void jshsji() {

    }

    @Test
    @Tag("Позитивные")
    @DisplayName("10.Проверить, что id всегда уникальный.")
    public void testGetPlayerUniqueId() {
        int playerId1 = service.createPlayer(NICKNAME);
        int playerId2 = service.createPlayer(NICKNAME + "ttt"); // почему не создается с рандомным nickname?
        Assertions.assertNotEquals(playerId1, playerId2);
    }

    @Test
    @Tag("Позитивный")
    @DisplayName("11.(нет json-файла) запросить список игроков")
    public void testEmptyListPlayrs() {
        Collection<Player> listPlayers = service.getPlayers();
        Assertions.assertEquals(0, listPlayers.size());

    }

    @Test
    @Tag("Позитивный")
    @DisplayName("12.Проверить создание игрока с 15 символами")
    public void testCreatePlayerWithCharacters15() {
        String MaxNickname = "Vicktoriansskay";
        int playerId = service.createPlayer(MaxNickname);
        Player playerById = service.getPlayerById(playerId);
        Assertions.assertEquals(MaxNickname, playerById.getNick());
    }

    @Test
    @Tag("Негативный")
    @DisplayName("1.удалить игрока, которого нет)")
    public void deletePlayer_withNonExistingId() {

        Assertions.assertThrows(NoSuchElementException.class, () -> service.deletePlayer(-1));
    }

    @Test
    @Tag("Негативный")
    @DisplayName("2.создать дубликат (имя уже занято)")
    public void testDublicateId() {
        service.createPlayer(NICKNAME);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.createPlayer(NICKNAME));
    }

    @Test
    @Tag("Негативный")
    @DisplayName("3.получить игрока по id, которого нет")
    public void testGetPlayerByIdDoesntExist() {

        Assertions.assertThrows(NoSuchElementException.class, () -> service.getPlayerById(9999));
    }

    //Баг-не дает ошибку при создании игрока с пустым ником
    @Test
    @Tag("Негативный")
    @DisplayName(value = "4.сохранить игрока с пустым ником ")
    public void testCreatePlayerNickNull() {
        try {
            Files.writeString(Path.of("./data.json"), "Vera");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(NICKNAME);
        Assertions.assertThrows(NullPointerException.class, () -> service.createPlayer(""));
    }

    // Баг-не дает ошибку при начислении отрицательного числа очков.
    @ParameterizedTest
    @ValueSource(ints = {-1, -50, -5000000})
    @Tag("Негативный")
    @DisplayName("5.начислить отрицательное число очков")
    public void testAddNegativePoints(int points) {

        int playerId = service.createPlayer(NICKNAME);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addPoints(playerId, points));
    }

    @Test
    @Tag("Негативный")
    @DisplayName("6.Накинуть очков игроку, которого нет ")
    public void testAddPointsPlayerByIdDoesntExist() {
        int playerId = 9999;
        int points = 234;
        Assertions.assertThrows(NoSuchElementException.class, () -> service.addPoints(playerId, points));
    }

    @Disabled
    @Test
    @Tag("Позитивный")
    @DisplayName("7.Проверить загрузку системы с другим json-файлом")
    public void testCrea() {
    }

    @Disabled
    @Test
    @Tag("Позитивный")
    @DisplayName("8.проверить корректность загрузки json-файла")
    public void testC() {
    }

    // Баг =- не дает ошибку при создании игрока с 16 символами
    @Test
    @Tag("Негативный")
    @DisplayName("9.Проверить создание игрока с 16 символами")
    public void testCreatePlayerWithCharacters16() {
        String LongNickname = "VicktoriansskayQ";
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.createPlayer(LongNickname));
    }
}
