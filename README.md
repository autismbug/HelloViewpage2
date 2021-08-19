# ViewPager2 使用说明书

## 零、Demo

[项目源码](https://github.com/autismbug/HelloViewpage2.git)

[演示 apk](https://github.com/autismbug/HelloViewpage2/releases/download/v1.0.0/helloViewPager2.apk)

如果对你有用，希望能给个 star，谢谢。

## 一、功能

官方关于使用 ViewPager2 创建滑动视图的说明：

> Swipe views allow you to navigate between sibling screens, such as tabs, with a horizontal finger gesture, or *swipe*. This navigation pattern is also referred to as *horizontal paging*. This topic teaches you how to create a tab layout with swipe views for switching between tabs, along with how to show a title strip instead of tabs.

大意是说，使用 ViewPager2 可以实现 Views 或页面的水平方向（常用水平，垂直也支持）的滑动。也可以结合 Tab 组件使用。

## 二、基本使用

### 2.1 依赖引用

```gradle
implementation "androidx.viewpager2:viewpager2:1.0.0"
```

### 2.2 版本说明

1.0.0 版本是 2019 年 11 月 20 日 更新的。

1.1.0-beta01 测试版本是 2021 年 8 月 4 日 ，最近才更新的。

具体的更新内容，和最新版本的信息，可以在[这个链接](https://developer.android.google.cn/jetpack/androidx/releases/viewpager2#androidx-deps)查到。

### 2.3 基本使用

ViewPager2 使用方式简单。学习需要掌握以下几个要素：XML 声明、定义 Adapter 、设置滑动监听。

#### 2.3.1 XML 布局中使用

```html
<androidx.viewpager2.widget.ViewPager2
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/pager"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

#### 2.3.2 常用 Adapter 类型

ViewPager2.java 部分源码：

```java
private void initialize(Context context, AttributeSet attrs) {
        mAccessibilityProvider = sFeatureEnhancedA11yEnabled
                ? new PageAwareAccessibilityProvider()
                : new BasicAccessibilityProvider();

        mRecyclerView = new RecyclerViewImpl(context);
        mRecyclerView.setId(ViewCompat.generateViewId());
        mRecyclerView.setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);

        mLayoutManager = new LinearLayoutManagerImpl(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);
        setOrientation(context, attrs);

        mRecyclerView.setLayoutParams(
                new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mRecyclerView.addOnChildAttachStateChangeListener(enforceChildFillListener());
 			  ...
}
```

通过 ViewPager2 的源码，可以看到它内部维护了一个 RecyclerView，来实现列表视图的显示和滑动控制。

所以，**RecyclerView.Adapter** 可以直接用于 ViewPager2 ，这个应该是大家在使用 RecyclerView 组件时经常用到的。

另外，ViewPager2 的依赖包中，还提供了 **FragmentStateAdapter** ,继承自RecyclerView.Adapter。主要用于 ViewPager2 和 Fragment 的结合使用。下文中会介绍如何使用。

<img src="https://image.autismbug.cn/2021-08-18-151043.png" alt="image-20210818231024635" style="zoom: 50%;" />

#### 2.3.3 滑动事件监听

```java
void registerOnPageChangeCallback(@NonNull OnPageChangeCallback callback)
```

通过该函数，注册 Page 变化的事件监听。

OnPageChangeCallback 类的源码如下：

```java
 public abstract static class OnPageChangeCallback {
        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         *
         * @param position Position index of the first page currently being displayed.
         *                 Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
         * @param positionOffsetPixels Value in pixels indicating the offset from position.
         */
        public void onPageScrolled(int position, float positionOffset,
                @Px int positionOffsetPixels) {
        }

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        public void onPageSelected(int position) {
        }

        /**
         * Called when the scroll state changes. Useful for discovering when the user begins
         * dragging, when a fake drag is started, when the pager is automatically settling to the
         * current page, or when it is fully stopped/idle. {@code state} can be one of {@link
         * #SCROLL_STATE_IDLE}, {@link #SCROLL_STATE_DRAGGING} or {@link #SCROLL_STATE_SETTLING}.
         */
        public void onPageScrollStateChanged(@ScrollState int state) {
        }
    }
```

有三个回调方法:

1. onPageScrolled ，返回 Page 滑动位置偏移或像素的变化。
2. onPageSelected ，滑动后的 page position。
3. onPageScrollStateChanged，滑动状态的变化。

从一个 Page 滑动到另一个 Page ，Callback 的回调情况：

```java
2021-08-18 23:28:14.046  onPageSelected() called with: position = 0
2021-08-18 23:28:14.047  onPageScrolled() called with: position = 0, positionOffset = 0.0, positionOffsetPixels = 0
2021-08-18 23:28:37.333  onPageScrollStateChanged() called with: state = 1
2021-08-18 23:28:37.350  onPageScrolled() called with: position = 0, positionOffset = 0.016666668, positionOffsetPixels = 18
......
2021-08-18 23:28:37.427  onPageScrolled() called with: position = 0, positionOffset = 0.20277777, positionOffsetPixels = 219
2021-08-18 23:28:37.431  onPageScrollStateChanged() called with: state = 2
2021-08-18 23:28:37.450  onPageSelected() called with: position = 1
2021-08-18 23:28:37.451  onPageScrolled() called with: position = 0, positionOffset = 0.28611112, positionOffsetPixels = 309
......
2021-08-18 23:28:37.717  onPageScrolled() called with: position = 0, positionOffset = 0.99814814, positionOffsetPixels = 1078
2021-08-18 23:28:37.734  onPageScrolled() called with: position = 1, positionOffset = 0.0, positionOffsetPixels = 0
2021-08-18 23:28:37.734  onPageScrollStateChanged() called with: state = 0
```

```
onPageScrollStateChanged 滑动状态 state:
SCROLL_STATE_IDLE = 0
SCROLL_STATE_DRAGGING = 1
SCROLL_STATE_SETTLING = 2
```

观察日志，回调的特征：

1. 初次加载，会回调 onPageSelected 和 onPageScrolled 方法，position 为 0。
2. 向右滑动一页时，首先触发 onPageScrollStateChanged 回调，state 为 SCROLL_STATE_DRAGGING 。然后 onPageScrolled 多次回调，可以看到位置偏移量的变化。在 onPageScrolled 多次回调中间，会回调 onPageScrollStateChanged 方法，state 变为 SCROLL_STATE_SETTLING 位置固定。然后回调 onPageSelected ，position 为滑动后的位置。
3. 最后一次 onPageScrolled 回调，position 变为 1。之后回调 onPageScrollStateChanged ，state 变为 SCROLL_STATE_IDLE 。

## 三、使用方式

### 3.1 + View

#### 3.1.1 效果演示

<img src="https://image.autismbug.cn/2021-08-19-054524.gif" alt="views" style="zoom:50%;" />

#### 3.1.2 Adapter 代码实现

首先定义一个 item xml 布局。

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center">

    <TextView
        android:id="@+id/tv_text"
        android:background="@color/black"
        android:layout_width="match_parent"
        android:layout_height="280dp"
        android:gravity="center"
        android:textColor="#ffffff"
        android:textSize="22sp" />
</LinearLayout>
```

自定义 ViewAdapter。

```kotlin
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter : RecyclerView.Adapter<ViewAdapter.PagerViewHolder>() {
    var data: List<Int> = ArrayList()

    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTextView: TextView = itemView.findViewById(R.id.tv_text)
        private val colors = arrayOf("#CCFF99", "#41F1E5", "#8D41F1", "#FF99CC")

        fun bindData(i: Int) {
            mTextView.text = i.toString()
            mTextView.setBackgroundColor(Color.parseColor(colors[i]))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
```

#### 3.1.3 ViewPager2 配置

在 Activity 或 Fragment 中的布局文件，直接使用  ViewPager2 标签。

然后在代码中，配置 Adapter 便完成了，实现 3.1.1 中的效果。

```kotlin
    val viewAdapter = ViewAdapter()
    viewAdapter.data = listOf(1, 2, 3, 4)
    viewPager2 = findViewById(R.id.view_pager)
    viewPager2.apply {
      adapter = viewAdapter
    }
```

#### 3.1.4 使用场景

Banner ，轮播广告图。可以配合定时器，进行定时滚动展示。

### 3.2 + Fragment

#### 3.2.1 效果展示

整体效果，看上去与使用 RecyclerView.Aadapter + Views 的形式差不多。

前者在于局部控件，和 Fragment 配合，更多是整页的滑动。

这个例子中，增加了一些动画效果，及一屏多页的效果，在下文中会详细说明。

<img src="https://image.autismbug.cn/2021-08-19-054516.gif" alt="fragment" style="zoom:50%;" />

#### 3.2.2 Adapter 代码实现

ViewPager2 和 Fragment 配合使用， Adapter 前文也提到过，ViewPager2 的依赖包中，特别提供了 **FragmentStateAdapter** 。

使用起来比较方便，只用重写两个方法：getItemCount 和 createFragment。示例如下：

TestFragment 是用 AndroidStudio 生成的模板 BlankFragment ，并将两个入参，分别设置为页面的文本内容及背景颜色。

```kotlin
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    private val colors = arrayOf("#CCFF99", "#41F1E5", "#8D41F1", "#FF99CC")

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_MESSAGE -> TestFragment.newInstance("消息$position", colors[0])
            PAGE_CONTACT -> TestFragment.newInstance("通讯录$position", colors[1])
            PAGE_SETTING -> TestFragment.newInstance("设置$position", colors[2])
            PAGE_MINE -> TestFragment.newInstance("我$position", colors[3])
            else -> TestFragment.newInstance("$position", colors[0])
        }
    }

    companion object {
        const val PAGE_MESSAGE = 0
        const val PAGE_CONTACT = 1
        const val PAGE_SETTING = 2
        const val PAGE_MINE = 3
    }
}
```

#### 3.2.3 ViewPager2 配置

使用方式简单， 直接将 Adapter 实例化赋值给 ViewPager2  对象的 adapter 。

#### 3.2.4 场景

页面间的切换，支持手势滑动。支持过渡的动画。

还可以结合 Tab 组件，进行组件间的联动。

### 3.3 + TabLayout

#### 3.3.1 效果展示

<img src="https://image.autismbug.cn/2021-08-19-070802.gif" alt="tablayout" style="zoom:50%;" />

#### 3.3.2 代码实现

TabLayout 一般放在页面的顶部位置。在页面布局的 xml 中，用 com.google.android.material.tabs.TabLayout 标签声明。

TabLayout 组件是 material 包中的组件，Android Studio 新建项目，会自动引入这个依赖。

如果是老版本的项目，想要引入 TabLayout 组件，可以引入以下依赖，具体版本以官网为准。

```gradle
'com.google.android.material:material:1.3.0'
```

XML 中声明：
```xml
    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tab_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

代码中，通过 TabLayoutMediator 将 TabLayout 和 ViewPager2 进行绑定。

```kotlin
      val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
      TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
           //设置标签名称
           tab.text = "OBJECT ${(position + 1)}"
      }.attach()
```

这样就实现了，标签导航和页面滑动的联动。

#### 3.3.3 使用场景

一般可以用于类似新闻或者电商 ，各种类目页面间的切换。

### 3.4 + BottomNavigationView

#### 3.4.1 效果展示

<img src="https://image.autismbug.cn/2021-08-19-070755.gif" alt="bottomNavigation" style="zoom:50%;" />

#### 3.4.2 代码实现

监听 BottomNavigationView 的 setOnNavigationItemSelectedListener ，改变 ViewPager2 的 item。

监听 ViewPager2 的 onPageSelected ，改变 BottomNavigationView  的 item。

注意两组件的数量和位置要对应。

```kotlin
 				// 页面适配器
        val fragmentPagerAdapter = FragmentPagerAdapter(this)
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        viewPager2.apply {
            adapter = fragmentPagerAdapter
            // 不提前加载
            offscreenPageLimit = fragmentPagerAdapter.itemCount
            // 单动画效果
            setPageTransformer(ScaleInTransformer())
        }

        // 底部菜单
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        // 设置监听
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
              //设置 viewPager2 item 位置
                R.id.menu_messages -> {
                    viewPager2.setCurrentItem(0, true)
                 		// 如返回 false ，bottomNavigation 被点击的 item ，不会被置为选中状态
                    true
                }
                R.id.menu_contacts -> {
                    viewPager2.setCurrentItem(1, true)
                    true
                }
                R.id.menu_setting -> {
                    viewPager2.setCurrentItem(2, true)
                    true
                }
                R.id.menu_mine -> {
                    viewPager2.setCurrentItem(3, true)
                    true
                }
                else -> throw IllegalArgumentException("未设置的 menu position，请检查参数")
            }
        }

        // 监听页面变化
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
              	// 设置 bottomNavigation item 状态
                bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
```

#### 3.4.3 使用场景

带底部导航的多页面导航 APP 。

### 3.5 其它

#### 3.5.1 预加载

ViewPager2 的 offscreenPageLimit 属性。

设置为 ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT ，不进行预加载和缓存。

#### 3.5.2 过渡效果

支持单效果和多效果设置，都是通过 setPageTransformer 函数。

1. 单效果

   直接传入  ViewPager2.PageTransformer 的子类，即可以设置对应的效果。

   ViewPager2 包中提供了 MarginPageTransformer 页面边距效果，可以直接在项目中使用。

   也可以自定义实现  PageTransformer 。

2. 组合效果

   ViewPager2 包中的 CompositePageTransformer 类，该类中，维护了 List<PageTransformer>。

   可以设置多种 PageTransformer  组合效果。

下面提供几种效果的展示和代码，可以学习下自定义实现自己的效果：

##### 1. 深度变化效果

<img src="https://image.autismbug.cn/2021-08-19-070748.gif" alt="depth" style="zoom:50%;" />

```kotlin
import android.view.View
import androidx.viewpager2.widget.ViewPager2

private const val MIN_SCALE = 0.75f

class DepthPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    alpha = 1f
                    translationX = 0f
                    translationZ = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0,1]
                    // Fade the page out.
                    alpha = 1 - position

                    // Counteract the default slide transition
                    translationX = pageWidth * -position
                    // Move it behind the left page
                    translationZ = -1f

                    // Scale the page down (between MIN_SCALE and 1)
                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}
```

##### 2. 比例放大进入效果

<img src="https://image.autismbug.cn/2021-08-19-064422.gif" alt="scarle" style="zoom:50%;" />

```kotlin
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs

class ScaleInTransformer : ViewPager2.PageTransformer {
  private val mMinScale = DEFAULT_MIN_SCALE
  override fun transformPage(view: View, position: Float) {
    view.elevation = -abs(position)
    val pageWidth = view.width
    val pageHeight = view.height

    view.pivotY = (pageHeight / 2).toFloat()
    view.pivotX = (pageWidth / 2).toFloat()
    if (position < -1) {
      view.scaleX = mMinScale
      view.scaleY = mMinScale
      view.pivotX = pageWidth.toFloat()
    } else if (position <= 1) {
      if (position < 0) {
        val scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
        view.pivotX = pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
      } else {
        val scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
        view.pivotX = pageWidth * ((1 - position) * DEFAULT_CENTER)
      }
    } else {
      view.pivotX = 0f
      view.scaleX = mMinScale
      view.scaleY = mMinScale
    }
  }

  companion object {
    const val DEFAULT_MIN_SCALE = 0.85f
    const val DEFAULT_CENTER = 0.5f
  }
}
```

##### 3.缩放进入退出效果

<img src="https://image.autismbug.cn/2021-08-19-070727.gif" alt="zoom" style="zoom:50%;" />

```kotlin
private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class ZoomOutPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // Fade the page relative to its size.
                    alpha = (MIN_ALPHA +
                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}
```

##### 4. PageTransformer 页面边距效果

<img src="https://image.autismbug.cn/2021-08-19-064507.gif" alt="margin" style="zoom:50%;" />

#### 3.5.3 禁止手动滑动

```kotlin
viewPager2.isUserInputEnabled = true or false
```

#### 3.5.4 模拟滑动

```kotlin
 	viewPager2.beginFakeDrag()
	if (viewPager2.fakeDragBy(-300f)) {
  	viewPager2.endFakeDrag()
	}
```

#### 3.5.5 滑动方向

```kotlin
viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL or ViewPager2.ORIENTATION_HORIZONTAL
```

## 四、延伸扩展

1. 如何自定义 PageTransformer
2. ViewPager2 中 Fragment 的生命周期变化
3. 更多使用场景扩展

## 五、参考资料

[《还在用 ViewPager？是时候替换成 ViewPager2 了！》](https://mp.weixin.qq.com/s/9AqwB6lOOFZpHikFZk_2Pw)

[《Google 官方文档》](https://developer.android.google.cn/jetpack/androidx/releases/viewpager2#androidx-deps)

[《使用 ViewPager2 创建包含标签的滑动视图》](https://developer.android.google.cn/guide/navigation/navigation-swipe-view-2)

感谢！
