package com.megazone.springbootbackend.serviceImpl;

import com.megazone.springbootbackend.common.GenerateUUID;
import com.megazone.springbootbackend.model.dto.ClubAddDto;
import com.megazone.springbootbackend.model.dto.ClubModifiedDto;
import com.megazone.springbootbackend.model.entity.ClubEntity;
import com.megazone.springbootbackend.model.entity.QClubEntity;
import com.megazone.springbootbackend.model.response.FirstClubResponse;
import com.megazone.springbootbackend.repository.ClubRepository;
import com.megazone.springbootbackend.service.ClubService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {
    private final ClubRepository repository;
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    private final SessionFactory sessionFactory;


    // 일반적인 것.
    @Override
    @Transactional
    public void insertClubs(List<ClubAddDto> clubDtos) {
        List<ClubEntity> clubsEntities = clubDtos.stream().map(clubDto -> {
            String uuid = GenerateUUID.generateUUID();
            return ClubEntity.builder()
                    .id(uuid)
                    .abbr(clubDto.getAbbr())
                    .website(clubDto.getWebsite())
                    .stadium(clubDto.getStadium())
                    .name(clubDto.getName())
                    .status(clubDto.getStatus().equals("Y"))
                    .build();
        }).collect(Collectors.toList());
        repository.saveAll(clubsEntities);
    }

    // JPQL 이용
    @Override
    public FirstClubResponse selectFirstClub(String name) {
        String jpql = "select c from ClubEntity c where c.name like :name";
        TypedQuery<ClubEntity> query = em.createQuery(jpql, ClubEntity.class).setParameter("name", name);
        ClubEntity entity = query.getSingleResult();
        em.clear();
        return FirstClubResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .abbr(entity.getAbbr())
                .stadium(entity.getStadium())
                .website(entity.getWebsite())
                .build();
    }

    // queryDSL 이용
    @Override
    public List<FirstClubResponse> selectFirstClubs() {
        QClubEntity qClubs = new QClubEntity("c");
        List<ClubEntity> list = jpaQueryFactory.selectFrom(qClubs).where(qClubs.status.eq(true)).fetch();
        return list.stream().map(entity -> {
            return FirstClubResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .abbr(entity.getAbbr())
                    .stadium(entity.getStadium())
                    .website(entity.getWebsite())
                    .status(entity.isStatus())
                    .build();
        }).collect(Collectors.toList());
    }

    // HQL 이용
    @Override
    public List<FirstClubResponse> selectAllFirstClub() {
        Session session = sessionFactory.openSession();
        String hql = "select c from ClubEntity c";
        List<ClubEntity> list = session.createQuery(hql).list();
        session.close();
        return list.stream().map(clubsEntity -> {
            return FirstClubResponse.builder()
                    .id(clubsEntity.getId())
                    .name(clubsEntity.getName())
                    .abbr(clubsEntity.getAbbr())
                    .stadium(clubsEntity.getStadium())
                    .website(clubsEntity.getWebsite())
                    .status(clubsEntity.isStatus())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateClubs(ClubModifiedDto club) {
        Session session = sessionFactory.openSession();
        String hql = "update ClubEntity set name = :name, abbr = :abbr, website = :website, stadium = :stadium, status = :status where id like :id";
        session.createQuery(hql)
                .setParameter("id", club.getId())
                .setParameter("name", club.getName())
                .setParameter("abbr", club.getAbbr())
                .setParameter("website", club.getWebsite())
                .setParameter("stadium", club.getStadium())
                .setParameter("status", club.isStatus())
                .executeUpdate();
        session.close();
    }
}
