namespace cpp tutorial
namespace d tutorial
namespace dart tutorial
namespace java xenoteo.com.github.thrift.gen
namespace php tutorial
namespace perl tutorial
namespace haxe tutorial

service Transfer {
   i64 transferList(1: list<i32> data),
   i64 transferSet(1: set<i32> data),
   i64 transferMap(1: map<i32, i32> data)
}
