package cn.bit101.android.database

import android.util.Log
import androidx.datastore.preferences.core.*
import cn.bit101.android.App
import cn.bit101.android.App.Companion.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @author flwfdd
 * @date 2023/4/4 19:53
 * @description _(:з」∠)_
 */

const val TAG = "DataStore"

class DataStore {
    companion object {
        fun setString(key: Preferences.Key<String>, value: String) {
            CoroutineScope(Dispatchers.IO).launch {
                App.context.dataStore.edit { preferences ->
                    preferences[key] = value
                }
            }
        }

        fun setBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
            CoroutineScope(Dispatchers.IO).launch {
                App.context.dataStore.edit { preferences ->
                    preferences[key] = value
                }
            }
        }

        fun setFloat(key: Preferences.Key<Float>, value: Float) {
            CoroutineScope(Dispatchers.IO).launch {
                App.context.dataStore.edit { preferences ->
                    preferences[key] = value
                }
            }
        }

        // 登陆状态
        val LOGIN_STATUS = booleanPreferencesKey("login_status")
        val loginStatusFlow: Flow<Boolean?> = App.context.dataStore.data
            .map { preferences ->
                preferences[LOGIN_STATUS]
            }

        // 课程表模块配置
        // 学期
        val COURSE_SCHEDULE_TERM = stringPreferencesKey("course_schedule_term")
        val courseScheduleTermFlow: Flow<String?> = App.context.dataStore.data
            .map { preferences ->
                preferences[COURSE_SCHEDULE_TERM]
            }

        // 学期开始日期
        private const val COURSE_SCHEDULE_FIRST_DAY_KEY_STRING = "course_schedule_first_day"
        private val courseScheduleFirstDayPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        fun setCourseScheduleFirstDay(term: String, value: LocalDate) {
            setString(
                stringPreferencesKey(COURSE_SCHEDULE_FIRST_DAY_KEY_STRING + term),
                value.format(courseScheduleFirstDayPattern)
            )
        }

        fun getCourseScheduleFirstDayFlow(term: String): Flow<LocalDate?> {
            val key = stringPreferencesKey(COURSE_SCHEDULE_FIRST_DAY_KEY_STRING + term)
            return App.context.dataStore.data
                .map { preferences ->
                    if (preferences[key] == null) {
                        return@map null
                    }
                    try {
                        LocalDate.parse(
                            preferences[key] ?: "",
                            courseScheduleFirstDayPattern
                        )
                    } catch (e: Exception) {
                        Log.e(TAG, "courseScheduleFirstDayFlow", e)
                        null
                    }
                }
        }

        // 是否显示周六
        val COURSE_SCHEDULE_SHOW_SATURDAY =
            booleanPreferencesKey("course_schedule_show_saturday")
        val courseScheduleShowSaturdayFlow: Flow<Boolean> = App.context.dataStore.data
            .map { preferences ->
                preferences[COURSE_SCHEDULE_SHOW_SATURDAY] ?: true
            }

        // 是否显示周日
        val COURSE_SCHEDULE_SHOW_SUNDAY =
            booleanPreferencesKey("course_schedule_show_sunday")
        val courseScheduleShowSundayFlow: Flow<Boolean> = App.context.dataStore.data
            .map { preferences ->
                preferences[COURSE_SCHEDULE_SHOW_SUNDAY] ?: true
            }

        // 是否显示边框
        val COURSE_SCHEDULE_SHOW_BORDER =
            booleanPreferencesKey("course_schedule_show_border")
        val courseScheduleShowBorderFlow: Flow<Boolean> = App.context.dataStore.data
            .map { preferences ->
                preferences[COURSE_SCHEDULE_SHOW_BORDER] ?: false
            }

        // 是否高亮今日
        val COURSE_SCHEDULE_SHOW_HIGHLIGHT_TODAY =
            booleanPreferencesKey("course_schedule_show_highlight_today")
        val courseScheduleShowHighlightTodayFlow: Flow<Boolean> = App.context.dataStore.data
            .map { preferences ->
                preferences[COURSE_SCHEDULE_SHOW_HIGHLIGHT_TODAY] ?: true
            }

        // 是否显示节次分割线
        val COURSE_SCHEDULE_SHOW_DIVIDER =
            booleanPreferencesKey("course_schedule_show_divider")
        val courseScheduleShowDividerFlow: Flow<Boolean> = App.context.dataStore.data
            .map { preferences ->
                preferences[COURSE_SCHEDULE_SHOW_DIVIDER] ?: false
            }

        // 是否显示当前时间
        val COURSE_SCHEDULE_SHOW_CURRENT_TIME =
            booleanPreferencesKey("course_schedule_show_current_time")
        val courseScheduleShowCurrentTimeFlow: Flow<Boolean> = App.context.dataStore.data
            .map { preferences ->
                preferences[COURSE_SCHEDULE_SHOW_CURRENT_TIME] ?: true
            }

        // 时间表
        val COURSE_SCHEDULE_TIME_TABLE = stringPreferencesKey("course_schedule_time_table")
        val courseScheduleTimeTableFlow: Flow<String> = App.context.dataStore.data
            .map { preferences ->
                preferences[COURSE_SCHEDULE_TIME_TABLE] ?: ("08:00,08:45\n" +
                        "08:50,09:35\n" +
                        "09:55,10:40\n" +
                        "10:45,11:30\n" +
                        "11:35,12:20\n" +
                        "13:20,14:05\n" +
                        "14:10,14:55\n" +
                        "15:15,16:00\n" +
                        "16:05,16:50\n" +
                        "16:55,17:40\n" +
                        "18:30,19:15\n" +
                        "19:20,20:05\n" +
                        "20:10,20:55")
            }

        // 地图缩放倍率
        val MAP_SCALE = floatPreferencesKey("map_scale")
        val mapScaleFlow: Flow<Float> = App.context.dataStore.data
            .map { preferences ->
                preferences[MAP_SCALE] ?: 2f
            }
    }
}
