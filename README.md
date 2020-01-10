# java-feature-samples

java版本特性示例

## [java7-feature-samples](./java7-feature-samples)

Java7特性示例

### Phaser

- [Phaser示例](java7-feature-samples/src/test/java/cn/chenzw/java7/feature/util/concurrent/PhaserTest.java)

阶段器

## [java8-feature-samples](./java8-feature-samples)

Java8特性示例

### lambada

- [Lambad示例](java8-feature-samples/src/test/java/cn/chenzw/java8/feature/LambdaTest.java)：lambad写法示例

### stream接口

- [Colloctors类](java8-feature-samples/src/test/java/cn/chenzw/java8/feature/util/stream/ColloctorsTest.java): 对数组、列表进行转换
  - counting(): 统计元素个数
  - maxBy(): 获取最大值
  - minBy(): 获取最小值
  - summing: 求和
    - summingInt():
    - summingLong(): 
    - summingDouble(): 
  - averaging: 求平均值
    - averagingInt(): 
    - averagingLong(): 
    - averagingDouble():
  - joining(): 拼接
  - groupingBy(): 排序
  - partitioningBy(): 分组
  - toMap(): List/数组 转 Map对象
  - toSet(): List/数组 转 Set对象

- [Function接口](java8-feature-samples/src/test/java/cn/chenzw/java8/feature/util/function/FunctionTest.java)：接收一个参数，并返回一个结果
  - Function<T,R>: 接收一个参数并返回结果的函数
  - BiFunction<T,U,R>: 接受两个参数并返回结果的函数
  - DoubleFunction<R>: 接收一个double类型的参数并返回结果的函数
  - DoubleToIntFunction: 接收一个double类型的参数并返回int结果的函数
  - DoubleToLongFunction: 接收一个double类型的参数并返回long结果的函数
  - IntFunction<R>: 接收一个int类型的参数并返回结果的函数
  - IntToDoubleFunction: 接收一个int类型的参数并返回double结果的函数
  - IntToLongFunction: 接收一个int类型的参数并返回long结果的函数
  - LongFunction<R>: 接收一个long类型的参数并返回结果的函数
  - LongToDoubleFunction: 接收一个long类型的参数并返回double结果的函数
  - LongToIntFunction: 接收一个long类型的参数并返回int结果的函数
  - ToDoubleBiFunction<T,U>: 接收两个参数并返回double结果的函数
  - ToDoubleFunction<T>: 接收一个参数并返回double结果的函数
  - ToIntBiFunction<T,U>: 接收两个参数并返回int结果的函数
  - ToIntFunction<T>: 接收一个参数并返回int结果的函数
  - ToLongBiFunction<T,U>: 接收两个参数并返回long结果的函数
  - ToLongFunction<T>: 接收一个参数并返回long结果的函数

- [Consumer接口](java8-feature-samples/src/test/java/cn/chenzw/java8/feature/util/function/ConsumerTest.java): 接收一个参数，无返回值
  - BiConsumer：接收2个参数，无返回值
- [Predicate接口](java8-feature-samples/src/test/java/cn/chenzw/java8/feature/util/function/PredicateTest.java): 接收一个参数，返回Boolean值
  - BitPredicate: 接收2个参数，返回Boolean
- [Supplier接口](java8-feature-samples/src/test/java/cn/chenzw/java8/feature/util/function/SupplierTest.java): 无参数，返回一个结果


## [java9-feature-samples](./java9-feature-samples)

Java9特性示例

## [java10-feature-samples](./java10-feature-samples)

Java10特性示例

## [java11-feature-samples](./java11-feature-samples)

Java11特性示例

## [javax-feature-samples](./javax-feature-samples)

java其它特性示例

- [MessageFormat](javax-feature-samples/src/test/java/cn/chenzw/java/feature/text/MessageFormatTests.java): 字符串模版格式化(类似于String.format())

