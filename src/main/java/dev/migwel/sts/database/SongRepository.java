package dev.migwel.sts.database;

import dev.migwel.sts.database.entities.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends Repository<Song, Long> {
    Song save(Song song);

    Optional<Song> findByArtistAndTitle(String artist, String title);

    @Query(
            """
        select
            s
        from Song s
        where s.username =?1
        order by s.creationDate
        limit ?2
        offset ?3
    """)
    List<Song> findByUsername(String username, int limit, int offset);
}
