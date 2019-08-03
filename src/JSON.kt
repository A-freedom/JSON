import java.io.File
import java.io.FileInputStream
import java.util.*

class JSON(private val file: File){
    private var mainNode: Node = Node(null)
    private var point = mainNode
    fun readFile(){
        if (this.file.exists()){
            mainNode.data = Data("main",null)
            String(FileInputStream(file).readBytes()).findBrackets(mainNode)
        }else{
            error("error file is node exist")
        }
    }
    /* private functions */
    private fun String.search(nodeFather: Node){
        val text = toString()
        if (text.contains('{')){
            text.findLines().forEach {
                val nodeSon = Node(nodeFather)
                nodeSon.father = nodeFather
                nodeSon.data = it.toData()
                nodeFather.sonsList.addLast(nodeSon)
            }
            text.findBrackets(nodeFather)
        }else{
            text.split(',').forEach {
                val nodeSon = Node(nodeFather)
                nodeSon.data = it.toData()
                nodeFather.sonsList.addLast(nodeSon)

            }
        }
    }
    private fun String.findBrackets(node: Node) {
        var s = 0
        var count = 0
        for ( i in 0 until length){
            if (get(i) == '{'){
                if(count++ == 0){
                    s = i
                }

            }else if (get(i) == '}'){
                if (--count == 0){
                    node.nodeTitle = toString().findTile(s)
//                    println(toString().copy(s+1,i-1))
//                    println("+++++++++++++++++++++")
                    toString().copy(s+1,i-1).search(node)
                }
            }
        }

    }
    private fun String.findTile(s: Int): String {
        var point = s
        while (point > 0){
            point --
            if (get(point) == ','){break}
        }
        return copy(point+1 , s-1).filterNot { it == '\n' || it == ' ' || it == '"' || it == ','  || it == ':'}
    }
    private fun String.toData(): Data? {
        if (length <= 3) return null
        val matter = toString().filterNot { it == '\n' || it == ' ' || it == '"' || it == ',' }.split(':')
        return Data(matter[0],matter[1])
    }
    private fun String.findLines(): List<String> {
       var text = toString()
        var count = 0
        var start = 0
        for (i in 0 until length){
            when (get(i)){
                '{' -> if (count++ == 0) start = i
                '}' -> if (--count == 0) {
                    for ( i in 0..i-start){

                    }
                }
            }
        }
        println("text.length : ${text.length}")
        println("++++++++++++++++++++++++++++++++")
        return text.split(',')
    }
    private fun String.copy(start: Int, end: Int): String {
        var out = ""
        for (i in start..end)
            out += get(i)
        return out
    }


    class Node(var father: Node?){
        var sonsList = LinkedList<Node>()
        var data: Data? = null
        var nodeTitle = ""
        fun haveNodeInside():Boolean{return !sonsList.isEmpty()}
    }
    class Data(var name:String,var value: Any?)
    /* public functions */
//    fun getNode(name:String):Node?{
//            point.sonsList.forEach {
//                if (it.data!!.name == name){
//                    return it
//                }
//            }
//        return null
//    }
//    fun setTitle(title:String){
//        point.nodeTitle = title
//    }
//    fun addNode(name: String ,value: Any?){
//        val node = Node(point)
//        node.data = Data(name,value)
//        point.sonsList.add(node)
//    }
//    fun dropNode(name: String){
//        val node = getNode(name)
//        if (node == null){
//            error("table is not exist")
//        }else {
//            val father = node.father
//            if (father == null){
//                mainNode = Node(null)
//            }else{
//                getNode(name)?.let { father.sonsList.add(it) }
//            }
//        }
//    }
//    fun goTo(url:String){
//        val u = url.split('/')
//        var node = point
//        u.forEach {
//            try {
//                point = getNode(it)!!
//            } catch (e: Exception) {
//                error("error node [$it] is not exist")
//            }
//        }
//    }
//    fun gotToRoot(){
//        while (point.father != null){
//            point = point.father!!
//        }
//    }
//    fun export(){
//        gotToRoot()
//        fun export(level:Int,node: Node){
//            val space = "" ;for (i in 0..level){space.plus("  ")}
//            println("$level\"${node.nodeTitle}\" : {")
//            node.sonsList.forEach {
//                    if (!it.haveNodeInside()){
//                        println("\"${it.data?.name}\":\" ${it.data?.name}\"")
//                    }else {export(level+1,it)}
//            }
//
//        }
//        export(0,mainNode)
//
//    }


}







