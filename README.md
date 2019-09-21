# [ReadMe in English](https://github.com/Mararsh/MyBox/tree/master/en)

# MyBox：简易工具集
这是利用JavaFx开发的图形化界面程序，目标是提供简单易用的功能。免费开源。

## 下载与运行
每个版本编译好的包已发布在[Releases](https://github.com/Mararsh/MyBox/releases?)目录下（点击上面的`releases`页签）。   
如果github下载太慢，可以在云盘下载：  https://pan.baidu.com/s/1fWMRzym_jh075OCX0D8y8A#list/path=%2F

### 自包含程序包   
自包含的程序包无需java环境、无需安装、解包可用。    

| 平台 | 链接 | 大小 | 启动文件 |    
| - | - | -  | -  |    
| win | [MyBox-5.5-win.zip](https://github.com/Mararsh/MyBox/releases/download/v5.5/MyBox-5.5-win.zip)  | 208MB | MyBox.exe |       
| linux | [MyBox-5.5-linux.tar.gz](https://github.com/Mararsh/MyBox/releases/download/v5.5/MyBox-5.5-linux.tar.gz)  | 163MB  | bin/MyBox  |     
| mac | [MyBox-5.5-mac.dmg](https://github.com/Mararsh/MyBox/releases/download/v5.5/MyBox-5.5-mac.dmg)  | 164MB  |  MyBox-5.5.app   |   

双击或者用命令行执行包内的启动文件即可运行程序。可以把图片/文本/PDF文件的打开方式关联到MyBox，这样双击文件名就直接是用MyBox打开了。
    
### Jar包   
在已安装JRE或者JDK 12或更高版本（`Oracle java`或`Open jdk`均可）的环境下，可以下载jar包。   
 
| 平台 | 链接 | 大小 | 运行需要 |    
| - | - | -  | -  |   
| win | [MyBox-5.5-win-jar.zip](https://github.com/Mararsh/MyBox/releases/download/v5.5/MyBox-5.5-win-jar.zip)  | 92MB | Java 12或更高版本 |    
| linux | [MyBox-5.5-linux-jar.zip](https://github.com/Mararsh/MyBox/releases/download/v5.5/MyBox-5.5-linux-jar.zip)  | 96MB  | Java 12或更高版本 |    
| mac | [MyBox-5.5-mac-jar.zip](https://github.com/Mararsh/MyBox/releases/download/v5.5/MyBox-5.5-mac-jar.zip)  |  93MB  | Java 12或更高版本 |    
    
执行以下命令来启动程序：
<PRE><CODE>     java   -jar   MyBox-5.5.jar</CODE></PRE>
程序可以跟一个文件名作为参数、以用MyBox直接打开此文件。例如以下命令是打开此图片：
<PRE><CODE>     java   -jar   MyBox-5.5.jar   /tmp/a1.jpg</CODE></PRE>

### 对于高清晰屏幕的支持
Java 9以后已支持HiDPI，控件和字体都会适应当前清晰度配置。MyBox支持在线关闭/打开DPI敏感，修改时MyBox会自动重启。   
开发者需要注意的是：JavaFx虚拟屏幕的dpi不同于物理屏幕的dpi，对于窗口元素尺寸的计算还要考虑伸缩比。   

### 限制
在包含非英文字符的路径下无法启动自包含包，请解包到纯英文目录下。Jar包的运行不受此限制。


# 资源地址
项目主页：https://github.com/Mararsh/MyBox

源代码和编译好的包：https://github.com/Mararsh/MyBox/releases

在线提交软件需求和问题报告：https://github.com/Mararsh/MyBox/issues

云盘地址：https://pan.baidu.com/s/1fWMRzym_jh075OCX0D8y8A#list/path=%2F

在线帮助：https://mararsh.github.io/MyBox/mybox_help_zh.html


# 文档
| 文档名 | 版本 | 链接 |
| - | - | - |  
| 用户手册-综述 |  5.0 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-Overview-zh.pdf) |
| 用户手册-图像工具 | 5.0 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-ImageTools-zh.pdf) |
| 用户手册-PDF工具 | 5.0 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-PdfTools-zh.pdf) |
| 用户手册-桌面工具 | 5.0 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-DesktopTools-zh.pdf) |
| 用户手册-网络工具 | 5.0 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-NetworkTools-zh.pdf) |
| 开发指南 | 1.0 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.3/MyBox-DevGuide-1.0-zh.pdf) |
| 快捷键 | 1.0 | [html](https://mararsh.github.io/MyBox/mybox_shortcuts.html) |
| 开发日志 |  | [html](#devLog) |


# 实现基础
MyBox使用NetBeans 11.1和JavaFX Scene Builder 2.0开发：  
https://netbeans.org/   
https://www.oracle.com/technetwork/java/javafxscenebuilder-1x-archive-2199384.html    

基于以下开源软件/开源库：  

| 软件 | 角色 | 链接 |  
| - | - | - |  
| JavaFx | 图形化界面 | https://docs.oracle.com/javafx/2/ |     
| jai-imageio | 图像处理 | https://github.com/jai-imageio/jai-imageio-core |   
| PDFBox | PDF处理 | https://pdfbox.apache.org/ |   
| PDF2DOM | PDF转换html | http://cssbox.sourceforge.net/pdf2dom/ |   
| javazoom | MP3处理 | http://www.javazoom.net/index.shtml | 
| log4j | 日志处理 | https://logging.apache.org/log4j/2.x/ |      
| Derby | 数据库 | http://db.apache.org/derby/ |   
| GifDecoder | 解码不规范的Gif | https://github.com/DhyanB/Open-Imaging/ |   
| EncodingDetect | 检测文本编码 | https://www.cnblogs.com/ChurchYim/p/8427373.html |   
| Free Icons | 图标 | https://icons8.com/icons/set/home |  
| tess4j | OCR | http://tess4j.sourceforge.net/ |  



# 当前版本
当前是版本5.5，已实现的特点概述如下:
* [跨平台](#cross-platform)
* [国际化](#international)
* [PDF工具](#pdfTools)
* [图像工具](#imageTools)
    - [查看图像](#viewImage)
    - [浏览图像](#browserImage)  
    - [图像处理](#imageManufacture)   
    - [调色盘](#ColorPalette)
    - [图像数据](#ImageData)  
    - [图片转换](#imageConvert)
    - [识别图像中的文字](#imageOCR)
    - [多帧图像文件](#multiFrames)
    - [多图合一](#multipleImages)
    - [图像局部化](#imagePart)
    - [大图片的处理](#bigImage)
    - [其它](#imageOthers)
* [数据工具](#dataTools)
    - [矩阵计算](#matrixTool)
    - [色彩空间](#colorSpaces)
* [文件工具](#fileTools)
    - [目录管理](#directoriesArrange)
    - [编辑文本](#editText)
    - [编辑字节](#editBytes)
    - [其它](#fileOthers)  
* [媒体工具](#MediaTools)
* [网络工具](#netTools)
    - [网页编辑器](#htmlEditor)
    - [微博截图工具](#weiboSnap)
* [设置](#settings)
* [窗口](#windows)
* [帮助](#helps)
* [配置](#Config)

## 跨平台<a name="cross-platform" />
MyBox用纯Java实现且只基于开源库，因此MyBox可运行于所有支持Java 12的平台。（MyBox v5.3以前的版本均基于Java 8）   

以下功能受限于特定平台：  

| 功能 | 可运行的平台 | 运行需要 |  
| - | - | -  |  
| 识别图像/PDF中的文字 | Windows  | 用户需自行下载数据文件 |    

## 国际化<a name="international" />
所有代码均国际化。可实时切换语言。目前支持中文、英文。扩展语言只需编辑资源文件。

## PDF工具<a name="pdfTools" />
1. 以网页模式查看PDF文件，可逐页查看和编辑页面和html。标签和缩略图。
2. 批量将PDF转换为网页，可选：每页保存为一个html、还是整个PDF保存为一个html；字体文件/图像文件是嵌入、单独保存、还是忽略。
3. 以图像模式查看PDF文件，可设置dpi以调整清晰度，可以把页面剪切保存为图片。
4. 在图像模式下，识别PDF页面中的文字（OCR）。批量识别时，可设置转换图像的色彩空间和像素密度。
   目前仅限windows平台。
5. 将PDF文件的每页转换为一张图片，包含图像密度、色彩、格式、压缩、质量、色彩转换等选项。
6. 将多个图片合成PDF文件，可以设置压缩选项、页面尺寸、页边、页眉、作者等。
   支持中文，程序自动定位系统中的字体文件，用户也可以输入ttf字体文件路径。
7. 压缩PDF文件的图片，设置JPEG质量或者黑白色阈值。
8. 合并多个PDF文件。
9. 分割PDF文件为多个PDF文件，可按页数或者文件数来均分，也可以设置起止列表。
10. 将PDF中的图片提取出来。可以指定页码范围。
11. 将PDF文件中的文字提取出来，可以定制页的分割行。
12. 修改PDF的属性，如：标题、作者、版本、修改时间、用户密码、所有者密码、用户权限等
13. PDF的批量处理。
14. 可设置PDF处理的主内存使用量。

## 图像工具<a name="imageTools" />

### 查看图像<a name="viewImage" />
1. 设置加载宽度：原始尺寸或指定宽度。
2. “选择模式”：处于选择模式时，剪裁、复制、另存，都是针对选择的区域，否则是针对整个图像。
3. 旋转可保存。
4. 删除、重命名、恢复。
5. 可选显示：坐标、横标尺、纵标尺、数据。
6. 查看图像的元数据和属性，可解码图像中嵌入的ICC特性文件。
7. 同目录下图像文件导览，多种文件排序方式。
### 浏览图像<a name="browserImage" />
1. 同屏显示多图，分别或者同步旋转和缩放。
2. 旋转可选保存。
3. 格栅模式：可选文件数、列数、加载宽度
4. 文件列表模式
5. 缩略图列表模式
6. 重命名、删除
### 图像处理<a name="imageManufacture" />
1. 粘贴板。
	-  数据来源：对图像整体或选择的部分做“复制”（CTRL+c）、系统粘贴板、系统中的图片文件、示例图片。
	-  管理粘贴板列表：增、删、清除、刷新，可设置最多保存数。
	-  编辑图像时随时可以按粘贴按钮（CTRL+v）以把粘贴板的第一张图贴到当前图片上，也可以双击粘贴板列表的项目以粘贴。
	-  在当前图片上拖拉被粘贴图片，调整大小和位置。
	-  粘贴选项：是否保持宽高比、混合模式、不透明度、旋转角度。
2. 剪裁：定义“范围”以设置要剪切的内容。可设置背景色，可选是否把剪切下来的部分放入粘贴板。
3. 伸缩：拖动锚点调整大小、按比例收缩、或设置像素。四种保持宽高比的选项。
4. 色彩：针对红/蓝/绿/黄/青/紫通道、饱和度、明暗、色相、不透明度，进行增加、减少、设值、过滤、取反色的操作。可选是否预乘透明。
5. 效果：清晰、对比度、海报（减色）、阈值化、灰色、黑白色、褐色、浮雕、边沿检测、模糊、锐化、马赛克、磨砂玻璃。可选算法和参数。
   也可以通过定义和选择卷积核来制作效果。
6. 富文本：以网页形式编辑文本，在图片上拖放调整文本的大小和位置。可设置背景的颜色、不透明度、边沿宽度、圆角大小，可设置文字的旋转角度。
   由于是利用截屏实现，结果比较模糊，还没有好的解决办法。
7. 文字：设置字体、风格、大小、色彩、不透明度、阴影、角度，可选是否轮廓、是否垂直，点击图片定位文字。
8. 画笔：
	-  折线：多笔一线。可选画笔的宽度、颜色、是否虚线、不透明度。
	-  线条：一笔一线。可选画笔的宽度、颜色、是否虚线、不透明度。
	-  橡皮檫：一笔一线。总是透明色，可选画笔的宽度。
	-  磨砂玻璃：一点一画。可选画笔的宽度、模糊强度、形状（圆形还是方形）。
	-  马赛克：一点一画。可选画笔的宽度、模糊强度、形状（圆形还是方形）。
	-  形状：矩形、圆形、椭圆、多边形。可选画笔的宽度、颜色、是否虚线、不透明度、是否填充、填充色。
9. 变形：斜拉、镜像、旋转，可设置参数。
10. 圆角：把图像四角改为圆角，可设置背景色、圆角大小。
11. 阴影：可设置背景色、阴影大小、是否预乘透明。
12. 边沿：模糊边沿，可设置是否预乘透明；拖动锚点以调整边沿；按宽度加边；按宽度切边；按颜色切边。可选四边、颜色。
13. 图片历史：
	- 对于图片的每一次修改，工具可以自动保存为图片历史。可选是否把“加载”也记录为历史。
	- 管理历史：删除、清除、选择并恢复为当前图片，可设置最多保存的历史个数。
	- 对上一步的撤销（CTRL+z）和重做（CTRL+y）。可以随时恢复原图（CTRL+r）。也可以选择历史列表中任意图片来恢复。
14. 参照图：可以打开其它图片以作对比。
15. “范围”：定义操作针对的像素内容，既可定义区域、定义颜色匹配规则，也可同时定义区域和颜色匹配。
	- 定义区域：可以是矩形、圆形、椭圆、多边形，区域可反选。
	- 定义要匹配颜色列表，可以利用调色盘在图片上直接取色。
	- 选择颜色匹配的对象，可以是红/蓝/绿通道、饱和度、明暗、色相，色距可定义。颜色匹配结果可反选。
	- 抠图：匹配像素周围的像素、并按同一匹配规则持续扩散出去。多个像素点的匹配合集就是结果。
	- 轮廓：把背景透明的图片的轮廓自动提取出来，作为操作的范围。
	- 范围可作用于：复制、剪切、颜色、效果、和卷积。
	- 图片历史和参照图也可以定义“范围”，以便把“范围”内的部分复制到粘贴板中  
	- 保存和管理范围：增、删、改、清除，应用已保存的范围。    
16. 弹出图片：当前图片、图片历史、参照图都可以显示在弹出的新窗口中，可选择弹出窗口是否总是在最上面。 
17. 可选是否同步缩放当前图片、图片历史、参照图。
18. 修改已有的图片，或新建图片。
19. “按需可见”的界面布局：左右幕布式区域、上下风箱式菜单、多页签切换目标、子功能区更细化的显示/隐藏/调整。
20. 批量图像处理。
### 调色盘<a name="ColorPalette" />
1. 可保存上千种色彩。可自动填写139种常用色彩。
2. 色块直观显示颜色。弹出颜色的名字（如果有）、十六进制值、rgb值、hsb值、不透明值。
3. 可选把调色盘中颜色导出为html列表。
4. 可在当前图片、图片历史、或参照图上点击取色。
### 图像数据<a name="ImageData" />
1. 统计显示图像的数据：各颜色通道的均值/方差/斜率/中值/众数/最大/最小，以及直方图。
2. 直方图的颜色通道可多选。
3. 可针对选择的矩形区域做统计显示。
### 图片转换<a name="imageConvert" />
1. 可选图像文件的格式，包括：png,jpg,bmp,tif,gif,wbmp,pnm,pcx，raw。
2. 可选颜色空间，包括：sRGB、Linear sRGB、ECI RGB、Adobe RGB、Apple RGB、Color Match RGB、ECI CMYK、Adobe CMYK(多种)、灰色、黑白色。
3. 可选外部ICC特性文件作为转换的依据。
4. 对于jpg/png格式可选是否嵌入ICC特性文件，对于Tif格式必选嵌入。
5. 可选对透明通道（如果有）的处理：保留、删除、预乘并保留、预乘并删除。
6. 可选压缩类型和质量。
7. 对于黑白色，可选二值化算法：OTSU、缺省、或输入预置，可选是否抖动处理。
8. 批量转换。
### 识别图像中的文字<a name="imageOCR" />
1. 用户可以安装Tesseract-OCR并在MyBox中指定它的数据文件目录，或者不安装Tesseract-OCR而单独下载所需的数据文件。
2. 可以选择语言。对于任何所选的语言，工具自动附加以下数据包名：eng（英文）、osd（检测方向和脚本）、equ（检测数学/方程式）
3. 同步显示图像和识别出的文字。可以设置需要识别的矩形区域。
4. 批量识别。  
目前仅限windows平台。
### 多帧图像文件<a name="multiFrames" />
1. 查看、提取多帧图像文件
2. 创建、编辑多帧tiff文件
3. 查看/提取/创建/编辑动画Gif文件。可设置间隔、是否循环、图片尺寸
### 多图合一<a name="multipleImages" />
1. 图片的合并。支持排列选项、背景颜色、间隔、边沿、和尺寸选项。
2. 将多个图片合成PDF文件
3. 添加透明通道
### 图像局部化<a name="imagePart" />
1. 图像的分割。支持按个数分割、按尺寸分割、和定制分割。可以保存为多个图像文件、多帧Tiff文件、或者PDF。
2. 图像的降采样。可以设置采样区域、采样比例。
3. 提取透明通道
### 大图片的处理<a name="bigImage" />
1. 评估加载整个图像所需内存,判断能否加载整个图像。
2. 若可用内存足够载入整个图像，则读取图像所有数据做下一步处理。尽可能内存操作而避免文件读写。
3. 若内存可能溢出，则采样读取图像数据做下一步处理。
4. 采样比的选择：即要保证采样图像足够清晰、又要避免采样数据占用过多内存。
5. 采样图像主要用于显示图像。已被采样的大图像，不适用于图像整体的操作和图像合并操作。
6. 一些操作，如分割图像、降采样图像，可以局部读取图像数据、边读边写，因此适用于大图像：显示的是采样图像、而处理的是原图像。
### 其它<a name="imageOthers" />
1. 支持图像格式：png,jpg,bmp,tif,gif,wbmp,pnm,pcx。可读Adobe YCCK/CMYK的jpg图像。
2. 像素计算器
3. 卷积核管理器

## 数据工具<a name="dataTools" />

### 矩阵计算<a name="matrixTool" />
1. 矩阵数据的编辑：
	-  对于输入或粘贴的数据，过滤特殊字符，以适应带格式的数据。
	-  自动把当前矩阵数据转变为行向量、列向量、或指定列数的矩阵。
	-  自动生成单位矩阵、随机方阵、或随机矩阵，可设置行/列数。   
2. 矩阵的一元计算：转置、行阶梯形、简化行阶梯形、行列式值-用消元法求解、行列式值-用余子式求解
	、逆矩阵-用消元法求解、逆矩阵-用伴随矩阵求解、矩阵的秩、伴随矩阵、余子式、归一化、
	、设置小数位数、设为整型、乘以数值、除以数值、幂。
3. 矩阵的二元计算：加、减、乘、克罗内克积、哈达马积、水平合并、垂直合并。
	
### 色彩空间<a name="colorSpaces" />
1. 绘制色度图
	-  标准数据的轮廓线：CIE 1931 2度观察者（D50）、CIE 1964 10度观察者（D50）、CIE RGB色域、ECI RGB色域、sRGB色域、
	   Adobe RGB色域、Apple RGB色域、PAL RGB色域、NTSC RGB色域、ColorMath ProPhoto RGB色域、SMPTE-C RGB色域。
	-  标准光源（白点）：A、C、D50、D55、D65、E。
	-  用户可填写刺激值或色坐标、或选择色彩，工具自动计算各种色彩空间对应的色彩数值、并把计算值显示在色度图上。
	-  用户可输入或用文件导入光谱数据，工具自动过滤掉特殊字符、并把光谱数据显示在色度图上。
	-  用户可以选择在色度图上显示/不显示以上数据。
	-  用户可选色度图的背景为透明/白色/黑色，可选轮廓线的点尺寸或线尺寸，可选是否显示格栅和波值。
	-  工具以表格和文本显示标准数据：CIE 1931 2度观察者1nm、CIE 1931 2度观察者5nm、CIE 1964 10度观察者1nm、CIE 1964 10度观察者5nm，
	   用户可导出数据的文本。
2. 编辑ICC色彩特性文件
	-  预置标准ICC文件：Java内嵌的ICC文件（包括sRGB、XYZ、PYCC、GRAY、LINEAR_RGB）、
	   ECI提供的ICC文件（包括ECI_CMYK、ECI_RGB_v2）和Adobe提供的ICC文件（包括Adobe RGB、Apple RGB、及多种CMYK ICC文件）。
	-  头部所有字段可编辑。在保存ICC文件时，工具自动计算“profile id”字段（MD5摘要）。
	-  标签表：标签、名字、类型、偏移、大小、描述、解码后的数据、数据的原始值（十六进制字节）
	-  可编辑的标签类型：Text、MultiLocalizedUnicode、Signature、DateTime、XYZ、Curve、ViewingConditions、Measurement、S15Fixed16Array。
	   当前版本不支持编辑LUT类型的标签。
	-  选项：把LUT表中的数据归一化到0~1。
	-  整个ICC数据被解析显示为XML，并可导出。未被解码的数据显示为十六进制字节。
	-  读入的ICC数据可以修改另存为新的ICC文件。
3. RGB色彩空间
	-  用户选择或输入RGB色彩空间（基色和白点）、选择或输入要适应的参考白点，工具自动计算色适应后的基色值，并展示计算过程。
	-  可设置小数位数。
	-  色适应算法可选：Bradford、XYZ Scaling、Von Kries。
	-  预置的标准RGB色彩空间包括：CIE RGB、ECI RGB、sRGB、Adobe RGB、Apple RGB、PAL RGB、NTSC RGB、ColorMath ProPhoto RGB、SMPTE-C RGB
    -  预置的标准光源包括CIE 1931和CIE 1964的：A、B、C、D50、D55、D65、D75、E、F1~F12。 
	-  工具以表格和文本显示：不同的标准RGB色彩空间、不同的标准光源、不同的算法所计算出的色适应后的基色值。用户可导出数据的文本。
4. 线性RGB到XYZ的转换矩阵
	-  用户选择或输入RGB色彩空间（基色和白点）、选择或输入XYZ空间的参考白点，工具自动计算线性RGB到XYZ的转换矩阵，并展示计算过程。
	-  以表格和文本显示：不同的标准RGB色彩空间、不同的XYZ空间参考白点、不同的算法所计算出的转换矩阵。用户可导出数据的文本。
5. 线性RGB到线性RGB的转换矩阵
	-  用户选择或输入源和目标的RGB色彩空间（基色和白点），工具自动计算源线性RGB到目标线性RGB的转换矩阵，并展示计算过程。
	-  工具以表格和文本显示：不同的标准RGB色彩空间之间以不同的算法所计算出的转换矩阵。用户可导出数据的文本。
6. 光源
	-  用户输入源颜色（相对值/色度坐标/刺激值）、选择或输入源白点和目标白点，工具自动计算色适应后的颜色值，并展示计算过程。
	-  工具以表格和文本显示标准光源的数据值、色温和说明。用户可导出数据的文本。
7. 色度适应矩阵
	-  用户选择或输入源白点和目标白点，工具自动计算色度适应矩阵，并展示计算过程。
	-  工具以表格和文本显示不同标准光源之间不同的算法的色度适应矩阵。用户可导出数据的文本。
	
## 文件工具<a name="fileTools" />

### 目录管理<a name="directoriesArrange" />
1. 目录/文件重命名，包含文件名和排序的选项。被重命名的文件可以全部恢复或者指定恢复原来的名字。
2. 目录同步，包含复制子目录、新文件、特定时间以后已修改文件、原文件属性，以及删除源目录不存在文件和目录，等选项。
3. 整理文件，将文件按修改时间或者生成时间重新归类在新目录下。此功能可用于处理照片、游戏截图、和系统日志等需要按时间归档的批量文件。
### 编辑文本<a name="editText" />
1. 自动检测或手动设置文件编码；改变字符集实现转码；支持BOM设置。
2. 自动检测换行符；改变换行符。显示行号。
3. 支持LF（Unix/Linux）、 CR（Apple）、 CRLF（Windows）。
4. 查找与替换。可只本页查找/替换、或整个文件查找/替换。计数功能。
5. 定位。跳转到指定的字符位置或行号。
6. 行过滤。条件：“包含任一”、“不含所有”、“包含所有”、“不含任一”。
7. 可累加过滤。可保存过滤结果。可选是否包含行号。
8. 字符集对应的编码：字节的十六进制同步显示、同步滚动、同步选择。
9. 分页。可用于查看和编辑非常大的文件，如几十G的运行日志。
	-  可以设置页尺寸。
	-  页面导航。
	-  先加载显示首页，同时后端扫描文件以统计字符数和行数；统计期间部分功能不可用；统计完毕自动刷新界面。
	-  对于跨页字符串，确保查找、替换、过滤的正确性。
10. 通用的编辑功能（复制/粘贴/剪切/删除/全选/撤销/重做/恢复）及其快捷键。
### 编辑字节<a name="editBytes" />
1. 字节被表示为两个十六进制字符。所有空格、换行、非法值将被忽略。
2. 常用ASCII字符的输入选择框。
3. 换行。仅用于显示、无实际影响。显示行号。可按字节数换行、或按一组字节值来换行。
4. 查找与替换。可只本页查找/替换、或整个文件查找/替换。计数功能。
5. 定位。跳转到指定的字节位置或行号。
6. 行过滤。条件：“包含任一”、“不含所有”、“包含所有”、“不含任一”。可累加过滤。可保存过滤结果。可选是否包含行号。
7. 选择字符集来解码：同步显示、同步滚动、同步选择。非字符显示为问号。
8. 分页。可用于查看和编辑非常大的文件，如几十G的二进制文件。
	- 可以设置页尺寸。
	- 页面导航。
	- 先加载显示首页，同时后端扫描文件以统计字节数和行数；统计期间部分功能不可用；统计完毕自动刷新界面。
	- 对于跨页字节组，确保查找、替换、过滤的正确性。若按字节数换行，则行过滤时不考虑跨页。
9. 通用的编辑功能（复制/粘贴/剪切/删除/全选/撤销/重做/恢复）及其快捷键。
### 其它<a name="fileOthers" />
1. 批量转换文件的字符集。
2. 批量转换文件的换行符
3. 切割文件。切割方式可以是：按文件数、按字节数、或按起止列表。
4. 合并文件。


## 媒体工具<a name="MediaTools" />
1. 记录系统粘贴板中的图像：保存或查看粘贴板中的图像，可选无损图像或压缩类型。
2. 闹钟，包括时间选项和音乐选项，支持铃音“喵”、wav铃音、和MP3铃音，可以在后端运行。


## 网络工具<a name="netTools" />

### 网页编辑器<a name="htmlEditor" />
1. 以富文本方式编辑本地网页或在线网页。（不支持FrameSet）
2. 直接编辑HTML代码。（支持FrameSet）
3. 网页浏览器显示编辑器内容、或在线网页。支持前后导览、缩放字体、截图页面为整图或者PDF文件。
4. 富文本页面、HTML代码、浏览器内容，这三者自动同步。
### 微博截图工具<a name="weiboSnap" />
1. 自动保存任意微博账户的任意月份的微博内容
2. 设置起止月份。
3. 确保页面完全加载，可以展开页面包含的评论、可以展开页面包含的所有图片。
4. 将页面保存为本地html文件。由于微博是动态加载内容，本地网页无法正常打开，仅供获取其中的文本内容。
5. 将页面截图保存为PDF。可以设置页尺寸、边距、作者、以及图片格式。
6. 将页面包含的所有图片的原图全部单独保存下来。
7. 实时显示处理进度。
8. 可以随时中断处理。程序自动保存上次中断的月份并填入作本次的开始月份。
9. 可以设置错误时重试次数。若超时错误则自动加倍最大延迟时间。

## 设置<a name="settings" />
1. 是否恢复界面上次尺寸、是否在新窗口中打开界面、是否弹出最近访问的文件/目录
2. 语言、字体大小、皮肤、按钮颜色和大小
3. 画笔/锚点的宽度和颜色、锚点是否实心
4. 图像是否显示坐标、横标尺、纵标尺。
5. 图像历史个数、图像最大显示宽度
6. 不支持Alpha时要替换的颜色（建议为白色）
7. PDF可用最大主内存
8. 退出程序时是否关闭闹钟
9. 基础参数：最大可用内存、是否关闭分辨率感知、数据目录。修改这几个参数将会使MyBox自动重启。
10. 清除个人设置、查看用户目录。

## 窗口<a name="windows" />
1. 开/关内存监视条
2. 开/关CPU监视条
3. 刷新/重置/全屏窗口
4. 关闭其它窗口
5. 最近访问的工具

## 帮助<a name="helps" />
1. MyBox快捷键列表
2. MyBox的属性
3. 用户手册（网址）
4. 开发指南（网址）

## 配置<a name="Config" />

### 安装目录、运行目录、数据目录
保存自包含程序或jar文件的目录称为“安装目录”（尽管不需要安装）。  
执行启动MyBox命令所在的目录称为“运行目录”。  
MyBox实例读写数据的目录称为“数据目录”。   
例如，把MyBox-5.5.jar复制到A目录下，在B目录下运行命令启动它，而MyBox的数据目录可以配置为任意C目录。

### 初始化MyBox
例如，把MyBox的exe包解开并存放在目录“D:\tmp\MyBox”中，双击“MyBox.exe”，MyBox实际是在子目录“app”下启动的，因此运行目录是“D:\tmp\MyBox\app”。   
Mybox检查目录“D:\tmp\MyBox\app”，未发现文件“MyBox.ini”，于是自动初始化这个实例：   
1. 把“D:\tmp\MyBox\app”作为MyBox的缺省数据根目录，创建子目录“mybox”。  
2. 若在当前用户的根目录发现子目录“mybox”（MyBox旧版本的数据目录），则将此目录全部复制到“D:\tmp\MyBox\app\mybox”中   
3. 在“D:\tmp\MyBox\app”中创建文件“MyBox.ini”，并写入以下行以记录此实例的数据根目录：  
<PRE><CODE>     MyBoxDataRoot=D\:\\tmp\\MyBox\\app </CODE></PRE>

用户可以编辑“MyBox.ini”以修改MyBox的数据根目录，并手动将旧数据目录文件复制到新数据目录下。   
用户也可以在工具的“设置”界面上修改数据根目录，工具将自动复制旧数据。   

又如，把“MyBox-5.5.jar”复制到“d:\tmp\1”下，而在“d:\tmp\2”下启动工具，则此时运行目录是“d:\tmp\2”，数据将被初始化在运行目录下。   
因此，可以同时在不同目录下启动MyBox而彼此不受干扰。

### 配置文件
在运行目录下，有配置文件“MyBox.ini”，记录有工具启动时引用的参数，在线修改这些参数将会使MyBox自动重启：   
1. 数据根目录，如：   
<PRE><CODE>     MyBoxDataRoot=/home/mara/data/ </CODE></PRE>
2. JVM内存使用量，如：   
<PRE><CODE>     JVMmemory=-Xms3026m </CODE></PRE>
3. 是否“关闭分辨率感知”，如：  
<PRE><CODE>     DisableHidpi=false </CODE></PRE>


# 开发日志<a name="devLog" />
2019-9-19 版本5.5 基于tess4j支持识别图像和PDF中的文字。单图识别可选择矩形区域。PDF批量识别可设置转换图像的色彩空间和像素密度。   
生成windows/linux/mac的自包含程序包。    
优化代码：只用maven打包而脱离对java 8的依赖；利用最新jpackage制作自包含包。    
修正问题：上一版本中微博截图工具挂了；在mac上微博截图工具首次运行后再也无法使用；linux上点击链接则程序僵死；计算CIELuv和CIELab时不应该归一化。   

2019-9-15 版本5.4  “数据目录”改为“运行目录”而不是以前的“用户目录”。用配置文件来保存基础参数。   
在线修改运行参数：最大可用内存、是否关闭分辨率感知、数据目录。修改这几个参数将会使MyBox自动重启。   
基于pdf2dom，以网页模式查看PDF页面。批量把PDF转换为网页。    
重构图像处理的界面：左右幕布式区域、上下风箱式菜单、多页签切换目标、子功能区更细化的显示/隐藏/调整。“按需可见”。   
粘贴板：保存多个来源的图片以供粘贴，在图片上拖拉来调整位置和大小，可选混合模式，可旋转被粘贴图片。提供示例图片。  
调色盘：可保存上千种色彩，可自动填写139种常用色彩，可导出为html页面，可在当前图片、图片历史、或参照图上点击取色。  
新的范围类型“轮廓”：把背景透明的图片的轮廓自动提取出来，作为操作的范围。提供示例图片。   
保存和管理图像处理的"范围"：增、删、改、清除，应用已保存的范围。   
统一快捷键，并提供帮助页面。  
优化代码：用公开的API替换掉内部类引用。确保单例程任务互斥进入和干净退出。写文件时先写到临时文件中以免意外导致源文件损害。        
修正问题。上一版本中以下工具失效：“修改PDF属性”、“压缩PDF”、“分割PDF”。阴影处理和3种混合模式中遗漏对于透明像素的处理。      
    
2019-8-8 版本5.3 迁移至： netbeans 11 + Java 12。    
优化批量处理界面：可添加目录、展开目录、过滤文件名、选择如何处理重名文件。    
优化图像转换：可选择更多的颜色空间并支持引用外部ICC特性文件、可选图像嵌入ICC特性文件、可选对透明通道的处理。    
优化图像元数据的解码：可读取图像中嵌入的ICC特性文件。    
优化代码：利用匿名类和嵌入fxml尽可能减少重复代码；整理类继承的关系；使项目配置文件支持多平台构建。    
初版《开发指南》。    
修正问题：”图像处理-颜色-透明度“的预乘透明算法用错了；在linux上另存图像时未自动添加扩展名而导致保存失败；    
linux上无法打开链接；ICC特性文件版本解码/编码错误、数据太多时界面会僵住、未解码的数据会导致xml无法生成。    
    
2019-6-30 版本5.2 图像解码：可读Adobe YCCK/CMYK的jpg图像；读取和显示多帧图像文件中所有图像的属性和元数据。    
PDF工具：标签（目录）和缩略图；可修改PDF文件的属性，如作者、版本、用户密码、用户权限、所有者密码等。    
编辑矩阵数据：适应带格式的输入数据；自动把当前矩阵数据转变为行向量、列向量、或指定列数的矩阵；    
自动生成单位矩阵、随机方阵、或随机矩阵。    
矩阵一元计算，包括转置、行阶梯形、简化行阶梯形、行列式值-用消元法求解、行列式值-用余子式求解、逆矩阵-用消元法求解、    
逆矩阵-用伴随矩阵求解、矩阵的秩、伴随矩阵、余子式、归一化、设置小数位数、设为整型、乘以数值、除以数值、幂。    
矩阵二元计算，包括加、减、乘、克罗内克积、哈达马积、水平合并、垂直合并。    
色彩空间的工具：绘制色度图；编辑ICC色彩特性文件；RGB色彩空间基色的色适应；线性RGB与XYZ之间的转换矩阵；    
线性RGB到线性RGB的转换矩阵；颜色值的色适应；标准光源；色度适应矩阵。    
修正问题：微博截图工具经常“414 Request-URI Too Large”；按钮的提示在屏幕边沿闪烁；一些链接不可用。    
    
2019-5-1 版本5.1 界面：控件显示为图片，5种颜色可选，可选是否显示控件文字。    
简化小提示，以适应14英寸的笔记本屏幕。    
图像工具：提取/添加透明通道。    
修正若干问题，包括：图像处理中过滤透明像素的错误条件。    
劳动节快乐！    
    
2019-4-21 版本5.0 以拖拉锚点的方式选择图像操作的区域。    
“涂鸦”：在图像上粘贴图片、添加形状（矩形/圆形/椭圆/多边形）的线条或填充形状、绘制多笔一线或一笔一线。    
画笔的宽度、颜色、实虚可选。    
查看图像：设置加载宽度；选择显示坐标和标尺；旋转可保存。    
浏览图像：缩略图格栅模式、缩略图列表模式、文件列表模式；可设置加载宽度；旋转可保存。    
图像处理：抖动处理扩展到除抠图以外的所有范围；利用预乘透明技术使不支持Alpha通道的格式也可展示透明效果；    
模糊边沿；低层实现阴影效果；拖动锚点以修改大小或边沿；多种形状的剪裁；文字可垂直。    
界面：只显示有用控件；足够但不叨扰的提示信息；快捷键/主键/缺省键；实时监视内存/CPU状态；    
查看JVM属性；刷新/重置窗口；保存和恢复界面尺寸；弹出最近访问的文件/目录；记录最近使用的工具。    
代码重构：以子类而不是分支语句来实现选择逻辑、把判断移至循环外；循环中避免浮点计算；理顺继承关系、减少重复代码；    
统一管理窗口的打开和关闭、避免线程残留。    
    
2019-2-20 版本4.9 图像对比度处理，可选算法。颜色量化时可选是否抖动处理。    
图像的统计数据分析，包含各颜色通道的均值/方差/斜率/中值/众数以及直方图。    
系统粘贴板内图像的记录工具。    
随时修改界面字体。    
查看图像：可选择区域来剪裁、复制、保存。    
    
2019-1-29 版本4.8 以图像模式查看PDF文件，可以设置dpi以调整清晰度，可以把页面剪切保存为图片。    
文本/字节编辑器的“定位”功能：跳转到指定的字符/字节位置、或跳转到指定的行号。    
切割文件：按文件数、按字节数、或按起止列表把文件切割为多个文件。    
合并文件：把多个文件按字节合并为一个新文件。    
程序可以跟一个文件名作为参数，以用MyBox直接打开此文件。    
在Windows上可以把图片/文本/PDF文件的打开方式缺省关联到MyBox.exe，可以在以双击文件时直接用MyBox打开。    
    
2019-1-15 版本4.7  编辑字节：常用ASCII字符的输入选择框；按字节数、或按一组字节值来换行；查找与替换，本页或整个文件，计数功能；    
行过滤，“包含任一”、“不含所有”、“包含所有”、“不含任一”，累加过滤，保存过滤结果，是否包含行号；    
选择字符集来解码，同步显示、同步滚动、同步选择；    
分页，可用于查看和编辑非常大的文件，如几十G的二进制文件，设置页尺寸，对于跨页字节组，确保查找、替换、过滤的正确性。    
批量改变文件的换行符。    
合并“文件重命名”和“目录文件重命名”。    
图像模糊改为“平均模糊”算法，它足够好且更快。    
    
2018-12-31 版本4.6  编辑文本：自动检测换行符；转换换行符；支持LF（Unix/Linux）、CR（iOS）、CRLF（Windows）。    
查找与替换，可只本页查找、或整个文件查找。    
行过滤，匹配类型：“包含字串之一”、“不包含所有字串”，可累加过滤，可保存过滤结果。    
分页：可用于查看和编辑非常大的文件，如几十G的运行日志；可设置页尺寸；对于跨页字符串确保查找、替换、过滤的正确性。    
先加载显示首页，同时后端扫描文件以统计字符数和行数；统计期间部分功能不可用；统计完毕自动刷新界面。    
进度等待界面添加按钮“MyBox”和“取消”，以使用户可使用其它功能、或取消当前进程。    
    
2018-12-15 版本4.5 文字编码：自动检测或手动设置文件编码；设置目标文件编码以实现转码；支持BOM设置；    
十六进制同步显示、同步选择；显示行号。批量文字转码。    
图像分割支持按尺寸的方式。    
可将图像或图像的选中部分复制到系统粘贴板（Ctrl-c）。    
在查看图像的界面可裁剪保存。    
    
2018-12-03 版本4.4 多帧图像文件的查看、提取、创建、编辑。支持多帧Tiff文件。    
对于所有以图像文件为输入的操作，处理多帧图像文件的情形。    
对于所有以图像文件为输入的操作，处理极大图像（加载所需内存超出可用内存）的情形。自动评估、判断、给出提示信息和下一步处理的选择。    
对于极大图像，支持局部读取、边读边写的图像分割，可保存为多个图像文件、多帧Tiff、或者PDF。    
对于极大图像，支持降采样。可设置采样区域和采样比率。    
    
2018-11-22 版本4.3 支持动画Gif。查看动画Gif：设置间隔、暂停/继续、显示指定帧并导览上下帧。    
提取动画Gif：可选择起止帧、文件类型。    
创建/编辑动画Gif：增删图片、调整顺序、设置间隔、是否循环、选择保持图片尺寸、或统一设置图片尺寸、另存，所见即所得。    
更简洁更强力的图像处理“范围”：全部、矩形、圆形、抠图、颜色匹配、矩形中颜色匹配、圆形中颜色匹配；    
颜色匹配可针对：红/蓝/绿通道、饱和度、明暗、色相；可方便地增减抠图的点集和颜色列表；均可反选。    
归并图像处理的“颜色”、“滤镜”、“效果”、“换色”，以减少界面选择和用户输入。    
多图查看界面：可调整每屏文件数；更均匀地显示图片。    
    
2018-11-13 版本4.2 图像处理的“范围”：全部、抠图、矩形、圆形、色彩匹配、色相匹配、矩形/圆形累加色彩/色相匹配。    
“抠图”如PhotoShop的魔术棒或者windows画图的油漆桶。    
“范围”可应用于：色彩增减、滤镜、效果、卷积、换色。可简单通过左右键点击来确定范围。    
卷积管理器：可自动填写高斯分布值；添加处理边缘像素的选项。    
目录重命名：可设置关键字列表来过滤要处理的文件。    
调整和优化图像处理的代码。    
更多的快捷键。    
    
2018-11-08 版本4.1 图像的“覆盖”处理。可在图像上覆盖：矩形马赛克、圆形马赛克、矩形磨砂玻璃、圆形磨砂玻璃、或者图片。    
可设置马赛克或磨砂玻璃的范围和粒度；可选内部图片或用户自己的图片；可设置图片的大小和透明度。    
图像的“卷积”处理。可选择卷积核来加工图像。可批量处理。    
卷积核管理器。自定义（增/删/改/复制）图像处理的卷积核，可自动归一化，可测试。提供示例数据。    
图像滤镜：新增黄/青/紫通道。    
    
2018-11-04 版本4.0  图像色彩调整：新增黄/青/紫通道。尤其黄色通道方便生成“暖色”调图片。    
图像滤镜：新增“褐色”。可以生成怀旧风格的图片。    
图像效果：新增“浮雕”，可以设置方向、半径、是否转换为灰色。    
图像的混合：可设置图像交叉位置、可选择多种常用混合模式。    
在线帮助：新增一些关键信息。    
    
2018-10-26 版本3.9  内嵌Derby数据库以保存程序数据；确保数据正确从配置文件迁移到数据库。    
图像处理：保存修改历史，以便退回到前面的修改；用户可以设置历史个数。    
用户手册的英文版。    
    
2018-10-15 版本3.8 优化代码：拆分图像处理的大类为各功能的子类。    
优化界面控件，使工具更易使用。设置快捷键。    
图像处理添加三个滤镜：红/蓝/绿的单通道反色。水印文字可以设置为“轮廓”。    
    
2018-10-09 版本3.7 微博截图工具：利用Javascript事件来依次加载图片，确保最小间隔以免被服务器判定为不善访问，    
同时监视最大加载间隔以免因图片挂了或者加载太快未触发事件而造成迭代中断。    
图像处理“效果”：模糊、锐化、边沿检测、海报（减色）、阈值化。    
    
2018-10-04 版本3.6 微博截图工具：继续调优程序逻辑以确保界面图片全部加载；整理代码以避免内存泄露。    
降低界面皮肤背景的明亮度和饱和度。    
在文档中添加关于界面分辨率的介绍。    
    
2018-10-01 版本3.5 微博截图工具：调优程序逻辑，以确保界面图片全部加载。    
提供多种界面皮肤。    
    
2018-09-30 版本3.4 修正问题：1）微博截图工具，调整页面加载完成的判断条件，以保证页面信息被完整保存。    
2）关闭/切换窗口时若任务正在执行，用户选择“取消”时应留在当前窗口。    
新增功能：1）可以设置PDF处理的最大主内存和临时文件的目录；2）可以清除个人设置。    
    
2018-09-30 版本3.3 最终解决微博网站认证的问题。已在Windows、CentOS、Mac上验证。    
    
2018-09-29 版本3.2 微博截图功能：1）在Linux和Windows上自动导入微博证书而用户无需登录可直接使用工具。    
但在Mac上没有找到导入证书的途径，因此苹果用户只好登录以后才能使用。    
2）可以展开页面上所有评论和所有图片然后截图。    
3）可以将页面中所有图片的原图保存下来。（感觉好酷）    
    
2018-09-26 版本3.1 所有图像操作都可以批量处理了。修正颜色处理算法。    
设置缺省字体大小以适应屏幕分辨率的变化。用户手册拆分成各个工具的分册了。    
提示用户：在使用微博截图功能之前需要在MyBox浏览器里成功登录一次以安装微博证书、    
（正在寻求突破这一限制的办法。Mybox没有兴趣接触用户个人信息）。    
    
2018-09-18 版本3.0 微博截图工具：可以只截取有效内容（速度提高一倍并且文件大小减小一半）、    
可以展开评论（好得意这个功能！）、可以设置合并PDF的最大尺寸。    
修正html编辑器的错误并增强功能。    
    
2018-09-17 版本2.14 微博截图工具：设置失败时重试次数、以应对网络状况很糟的情况；    
当某个月的微博页数很多时，不合并当月的PDF文件，以避免无法生成非常大的PDF文件的情况（有位博主一个月发了36页微博~）。。    
    
2018-09-15 版本2.13 分开参照图和范围图。确保程序退出时不残留线程。批量PDF压缩图片。    
微博截图工具：自动保存任意微博账户的所有微博内容，可以设置起止月份，可以截图为PDF、也可以保存html文件    
（由于微博是动态加载内容，本地网页无法正常打开，仅供获取其中的文本内容）。    
如果微博修改网页访问方式，此工具将可能失效。    
    
2018-09-11 版本2.12 合并多个图片为PDF文件、压缩PDF文件的图片、合并PDF、分割PDF。     
支持PDF写中文，程序自动定位系统中的字体文件，用户也可以输入ttf字体文件路径。     
提示信息的显示更平滑友好。网页浏览器：字体缩放，设置截图延迟、截图可保存为PDF。    
    
2018-09-06 版本2.11 图片的合并，支持排列选项、背景颜色、间隔、边沿、和尺寸选项。    
网页浏览器，同步网页编辑器，把网页完整内容保存为一张图片。图片处理：阴影、圆角、加边。    
确保大图片处理的正确性和性能。    
    
2018-08-11 版本2.10 图像的分割，支持均等分割个和定制分割。使图像处理的“范围”更易用。    
同屏查看多图不限制文件个数了。    
    
2018-08-07 版本2.9 图像的裁剪。图像处理的“范围”：依据区域（矩形或圆形）和颜色匹配，可用于局部处理图像。    
    
2018-07-31 版本2.8 图像的切边、水印、撤销、重做。Html编辑器、文本编辑器。    
    
2018-07-30 版本2.7 图像的变形：旋转、斜拉、镜像。    
    
2018-07-26 版本2.6 增强图像的换色：可以选择多个原色，可以按色彩距离或者色相距离来匹配。支持透明度处理。    
    
2018-07-25 版本2.5 调色盘。图像的换色：可以精确匹配颜色、或者设置色距，此功能可以替换图像背景色、或者清除色彩噪声。    
    
2018-07-24 版本2.4 完善图像处理和多图查看：平滑切换、对照图、像素调整。    
    
2018-07-18 版本2.3 闹钟，包括时间选项和音乐选项，支持wav铃音和MP3铃音，可以在后端运行。感谢我家乖乖贡献了“喵”。    
    
2018-07-11 版本2.2 修正线程处理逻辑的漏洞。整理文件，将文件按修改时间或者生成时间重新归类在新目录下。    
此功能可用于处理照片、游戏截图、和系统日志等需要按时间归档的批量文件。    
    
2018-07-09 版本2.1 完善图片处理的界面，支持导览。    
目录同步，包含复制子目录、新文件、特定时间以后已修改文件、原文件属性，以及删除源目录不存在文件和目录，等选项。    
    
2018-07-06 版本2.0 批量提取PDF文字、批量转换图片。    
目录文件重命名，包含文件名和排序的选项，被重命名的文件可以全部恢复或者指定恢复原来的名字。    
    
2018-07-03 版本1.9 修正问题。提取PDF文字时可以定制页分割行。    
完善图像处理：参数化调整饱和度、明暗、色相；滤镜：灰色、反色、黑白色。    
    
2018-07-01 版本1.8 将PDF文件中的文字提取出来。处理图片：调整饱和度、明暗，或者转换为灰色、反色。    
    
2018-06-30 版本1.7 完善像素计算器。支持同屏查看最多十张图，可以分别或者同步旋转和缩放。    
    
2018-06-27 版本1.6 将图片转换为其它格式，支持色彩、长宽、压缩、质量等选项。    
提供像素计算器。新增图像格式：gif, wbmp, pnm, pcx。    
    
2018-06-24 版本1.5 提取PDF中的图片保存为原格式。    
支持批量转换和批量提取。感谢 “https://shuge.org/” 的帮助：书格提出提取PDF中图片的需求。    
    
2018-06-21 版本1.4 读写图像的元数据,目前支持图像格式：png, jpg, bmp, tif。    
感谢 “https://shuge.org/” 的帮助：书格提出图像元数据读写的需求。    
    
2018-06-15 版本1.3 修正OTSU算法的灰度计算；优化代码：提取共享部件；支持PDF密码；使界面操作更友好。    
    
2018-06-14 版本1.2 针对黑白色添加色彩转换的选项；自动保存用户的选择；优化帮助文件的读取。    
感谢 “https://shuge.org/” 的帮助：书格提出二值化转换阈值的需求。    
    
2018-06-13 版本1.1 添加：转换格式tiff和raw，压缩和质量选项，以及帮助信息。    
感谢 “https://shuge.org/” 的帮助：书格提出tiff转换的需求。    
    
2018-06-12 版本1.0 实现功能：将PDF文件的每页转换为一张图片，包含图像密度、类型、格式等选项，并且可以暂停/继续转换过程。    


# 主界面
![About](https://mararsh.github.io/MyBox/0.jpg)

![About](https://mararsh.github.io/MyBox/1.jpg)

![About](https://mararsh.github.io/MyBox/2.jpg)

![About](https://mararsh.github.io/MyBox/3.jpg)

![About](https://mararsh.github.io/MyBox/4.jpg)

![About](https://mararsh.github.io/MyBox/5.jpg)

![About](https://mararsh.github.io/MyBox/6.jpg)

![About](https://mararsh.github.io/MyBox/7.jpg)

![About](https://mararsh.github.io/MyBox/8.jpg)

![About](https://mararsh.github.io/MyBox/9.jpg)





