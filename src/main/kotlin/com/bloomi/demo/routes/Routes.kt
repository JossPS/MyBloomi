package com.bloomi.demo.routes

object Routes {
    const val USERS_BASE = "/api/users"
    const val FOUNDATIONS_BASE = "/api/foundations"
    const val ACTIVITIES_BASE = "/api/activities"

    const val USERS = "/users"
    const val FOUNDATIONS = "/foundations"
    const val ACTIVITIES = "/activities"

    const val JOIN_ACTIVITY = "/{userId}/join/{activityId}"
    const val USER_ACTIVITIES = "/{userId}/activities"
}