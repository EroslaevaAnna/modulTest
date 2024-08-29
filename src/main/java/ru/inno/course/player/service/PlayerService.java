package ru.inno.course.player.service;

import ru.inno.course.player.model.Player;

import java.util.Collection;

public interface PlayerService {
    // получить игрока по id
    Player getPlayerById(int id);

    // получить список игроков
    Collection<Player> getPlayers();

    // создать игрока (возвращает id нового игрока)
    int createPlayer(String nickname);

    // удалить игрока по id'шнику, вернет удаленного игрока
    Player deletePlayer(int id);

    // Добавить очков игроку. Возвращает обновленный счет
    int addPoints(int playerId, int points);

}
