## Getting started

To build the library run

```bash
  ./gradlew build
```

## Using Trees

Any `Comparable` data can be stored in trees.
We also provide access to the `KeyValue` class, which allows you to store a key-value pair in the nodes of the tree.

```kotlin
import lib.trees.AVLTree
import lib.trees.RBTree
import lib.trees.BSTree
import lib.trees.KeyValue

val alvTree = AVLTree<Int>() // instantiate empty AVL tree
val bsTree = BSTree<String>() // instantiate empty simple tree
val rbTree = RBTree<KeyValue<Int, String>>() // instantiate empty red-black tree with key-value
```

Each tree supports 3 basic operations: `add`, `contain`, `delete` and `get` (if you need to get value by key using
KeyValue)

```kotlin
avlTree.add(42)
bsTree.add("42")
rbTree.add(KeyValue(42, "42"))
bsTree.contain("42") // returns true
avlTree.contain(1) // returns false
rbTree.get(KeyValue(42, null))?.getValue() // returns "42"
```

Trees' nodes can be read-only accessed by `root` property.

```kotlin
avlTree.add(10)
avlTree.add(5)
avlTree.add(20)
avlTree.add(30)
avlTree.add(42)
// avlTree after balancing:
//             10
//    ┌─────────┴─────────┐
//    5                  30
//                     ┌──┴──┐
//                    20     42

avlTree.root?.data // 10
avlTree.root?.left?.data // 5
avlTree.root?.right?.data // 30
avlTree.root?.right?.right?.data // 42
avlTree.root?.right?.left?.data // 20
```

## Storing Trees

`teemEight` provides `JsonRepository`, `SqlRepository` and `Neo4jRepository` to save & load trees.

Each instance of repository is used to store exactly 1 tree type. To store different tree types several repositories can
be instantiated.
Repository must be provided with `Serialization` which describes how to serialize & deserialize any particular
type of tree.

`bstrees` is shipped with `AVLStrategy`, `RBStrategy` and `SimpleStrategy` to serialize & deserialize AVL trees,
Red-black trees and Simple BSTs respectively. As these strategies don't know anything about the data type stored in
trees' nodes, user must provide `serializeData` and `deserializeData` functions to them.

Different tree types can be stored in the same database (directory) by creating several repositories and passing them
same databases (directory paths).

### Using Neo4j

Before using this, you must have [Docker](https://www.docker.com/) (also see [docs](https://docs.docker.com/))

#### Before started

1. Run docker container with `docker-compose.yml`
2. Open http://localhost:7474
3. Create new user (default password: neo4j)
4. Change the password
5. You got this

#### Example

```kotlin
val username = "neo4j"
val password = "" // insert password to database here
val conf = Configuration.Builder()
    .uri("bolt://localhost")
    .credentials(username, password)
    .build()

fun serializeInt(data: Int) = SerializableValue(data.toString())

fun deserializeInt(data: SerializableValue) = data.value.toInt()

val avlRepo = Neo4jRepo(AVLStrategy(::serializeInt, ::deserializeInt), conf)


val tree = AVLTree<Int>()
val randomizer = Random(42)
val lst = List(15) { randomizer.nextInt(1000) }
lst.forEach { tree.add(it) }
avlRepo.save("test", tree)
val testTree = avlRepo.loadByName("test")
println(testTree.preOrder()) // output pre-order traversal of tree
```