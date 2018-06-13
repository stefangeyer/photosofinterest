package at.renehollander.photosofinterest

interface BaseAdapter<T> {

    val itemList: MutableList<T>

    fun setAll(items: List<T>) {
        this.itemList.clear()
        this.itemList.addAll(items)
        notifyAdapter()
    }

    fun addItem(item: T) {
        this.itemList.add(item)
        notifyAdapter()
    }

    fun removeItem(item: T) {
        this.itemList.remove(item)
        notifyAdapter()
    }

    fun getItemAt(position: Int): T = this.itemList[position]

    fun getItems(): List<T> = this.itemList

    fun notifyAdapter()
}