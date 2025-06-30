package com.bloomi.demo.repositories

import com.bloomi.demo.models.entities.Activity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository

interface ActivityRepository : MongoRepository<Activity, String>