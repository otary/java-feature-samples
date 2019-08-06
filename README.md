# java-feature-samples

java版本特性示例


## [java8-feature-samples](./java8-feature-samples)

#### lambada

- [Lambad示例](java8-feature-samples/src/test/java/cn/chenzw/java8/feature/LambdaTest.java)：lambad写法示例

#### stream接口

- [Colloctors接口](java8-feature-samples/src/test/java/cn/chenzw/java8/feature/util/stream/ColloctorsTest.java): 

- [Function接口]()：常用于构建器模式
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


- [Consumer](): 无返回值