package com.assignment.repository;

import com.assignment.entity.Playlist;
import com.assignment.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by manish on 18/07/17.
 */
public interface PlaylistRepository extends PagingAndSortingRepository<Playlist, Long> {


    /**
     * This will return a reverse sorted list of playlists based on weighed ranking where like ratio is given more weight and the weight of number of plays reduces as the playlist gets older
     * @param tags
     * @param count
     * @param pageable
     * @return List of playlist
     */
    @Query(value = "SELECT i.* " +
            "FROM playlist i INNER JOIN " +
            "(SELECT pt.playlist_id " +
                "FROM playlist_tags pt WHERE pt.tags_name IN (:tags) " +
                "GROUP BY pt.playlist_id " +
                "HAVING COUNT(DISTINCT pt.tags_name) = :c) t ON i.id = t.playlist_id " +
            "ORDER BY (10*(i.like_count/i.play_count) + (i.play_count)*((i.updated_at)/CURRENT_TIMESTAMP())  ) DESC" +
            " \n#pageable\n",
            countQuery = "SELECT COUNT(i.*)" +
                    "FROM playlist i INNER JOIN " +
                    "(SELECT pt.playlist_id " +
                    "FROM playlist_tags pt WHERE pt.tags_name IN (:tags) " +
                    "GROUP BY pt.playlist_id " +
                    "HAVING COUNT(DISTINCT pt.tags_name) = :c) t ON i.id = t.playlist_id",
            nativeQuery = true)
    Page<Playlist> findByTags(@Param("tags") Set<String> tags, @Param("c") int count, Pageable pageable);


    @Query(value =
                "SELECT DISTINCT(pt1.tags_name) FROM playlist_tags pt1 WHERE pt1.playlist_id IN " +
                        "(SELECT pt.playlist_id " +
                        "FROM playlist_tags pt WHERE pt.tags_name IN (:tags) " +
                        "GROUP BY pt.playlist_id " +
                        "HAVING COUNT(DISTINCT pt.tags_name) = :c)",
            nativeQuery =  true)
    Set<Tag> findRelatedTags(@Param("tags") Set<String> tags, @Param("c") int count);

}
