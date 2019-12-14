package com.vladmihalcea.book.hpjp.hibernate.transaction.spring.jpa;

import com.vladmihalcea.book.hpjp.hibernate.forum.dto.PostDTO;
import com.vladmihalcea.book.hpjp.hibernate.transaction.forum.Post;
import com.vladmihalcea.book.hpjp.hibernate.transaction.spring.jpa.config.JPATransactionManagerReleaseAfterStatementConfiguration;
import com.vladmihalcea.book.hpjp.hibernate.transaction.spring.jpa.service.ReleaseAfterStatementForumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * This test case demonstrates what happens when you enable the AFTER_STATEMENT
 * Hibernate connection release mode when using a RESOURCE_LOCAL JPA transaction
 * with Spring.
 *
 * Currently, this mode is disabled, as it will make the test fail.
 * To enable the AFTER_STATEMENT release mode, open the {@link JPATransactionManagerReleaseAfterStatementConfiguration}
 * file and pass the {@code DELAYED_ACQUISITION_AND_RELEASE_AFTER_STATEMENT} setting to the
 * {@code CONNECTION_HANDLING} Hibernate configuration property.
 *
 * @author Vlad Mihalcea
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPATransactionManagerReleaseAfterStatementConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JPATransactionManagerReleaseAfterStatementTest {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReleaseAfterStatementForumService releaseAfterStatementForumService;

    @Test
    public void test() {
        Post newPost = releaseAfterStatementForumService
        .newPost(
            "High-Performance Java Persistence"
        );

        PostDTO postDTO = releaseAfterStatementForumService
        .savePostTitle(
            newPost.getId(),
            "High-Performance Java Persistence, 2nd edition"
        );

        assertEquals(
            "High-Performance Java Persistence, 2nd edition",
            postDTO.getTitle()
        );
    }
}