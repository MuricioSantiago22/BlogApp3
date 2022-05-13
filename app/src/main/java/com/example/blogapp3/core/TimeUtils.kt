package com.example.blogapp3.core

import java.util.concurrent.TimeUnit

private const val SECOND_MILLIS = 1
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLLIS

object TimeUtils {

    fun getTimeAgo(time:Int): String {

        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        if(time > now|| time <= 0){
            return "in the future"
        }

        val diff = now - time
        return when{
            diff < MINUTE_MILLIS -> "Just now"
            diff < 2 * MINUTE_MILLIS -> "a minute ago"
            diff < 60 * MINUTE_MILLIS -> "${diff/ MINUTE_MILLIS} minutes ago"
            diff < 2 * HOUR_MILLLIS -> "an hour ago"
            diff < 24 * HOUR_MILLLIS -> "${diff/ HOUR_MILLLIS}hours ago"
            diff <48 * HOUR_MILLLIS -> "yesterday"
            else -> "${diff / DAY_MILLIS} days ago"
        }
    }


}