package cn.bit101.android.ui.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import cn.bit101.android.database.CourseScheduleEntity

// 课程详情对话框
@Composable
fun CourseDetailDialog(course: CourseScheduleEntity, showDialog: MutableState<Boolean>) {
    AlertDialog(
        modifier = Modifier.fillMaxSize(0.9f),
        onDismissRequest = {
            showDialog.value = false
        },
        title = {
            Text(text = course.name)
        },
        text = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Item(title = "时空坐标：", content = course.description)
                Item("课程号：", course.number)
                Item("授课教师：", course.teacher)
                Item("学分：", course.credit.toString())
                Item("学时：", course.hour.toString())
                Item("课程性质：", course.type)
                Item("课程类别：", course.category)
                Item("开课单位：", course.department)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                }
            ) {
                Text("关闭")
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}

@Composable
private fun Item(title: String, content: String) {
    Spacer(modifier = Modifier.height(10.dp))
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f)
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseDetailPreview() {
    val show = remember { mutableStateOf(false) }
    CourseDetailDialog(
        CourseScheduleEntity(
            0,
            "2020-2021-2",
            "高等数学",
            "张三,李四",
            "教学楼101",
            "第1-16周",
            "[1][2][3][4][5][6][7][8][9][10][11][12][13][14][15][16]",
            1,
            1,
            2,
            "良乡校区",
            "123456",
            4,
            64,
            "必修",
            "文化课",
            "数学系"
        ),
        show
    )
}
