package org.launchcode.persistent.models.data;

import org.launchcode.persistent.models.Job;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface JobRepository extends CrudRepository<Job, Integer> {
    Iterable<Job> findAll();
}
