package com.project.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter // 모든 필드는 접근이 가능해야 한다.
@ToString // 쉽게 출력 가능하게 관찰
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields { // 빨간줄이 나오면 프라이머리키가 없다.
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 번호를 생성
    private Long id;

    // Setter 도메인에서 수정이 가능함
    // Setter를 각 필드에 적용하는 이유는 특정한 유저가 중요한 id에 접근을 막기위해
    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그

    // 양방향 바인딩 <ArticleComment를 맵핑>
    @ToString.Exclude // ToString으로 인해 순환참조로 메모리저하 방지
    @OrderBy("id") // 정렬기준
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {} // 코드 밖에서 new 생성 못함

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // of는 이런이런 값이 필요하다고 가이드 해줌
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // 컬렉션 또는 리스트에 넣거나 중복요소제거, 정렬 비교를 해야함 (동일성, 동등성검사) 롬복 이용x
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false; // java 14 pattern matching
        return id != null && id.equals(article.id); // id도 not null 이라고 확실하게 써줌
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}