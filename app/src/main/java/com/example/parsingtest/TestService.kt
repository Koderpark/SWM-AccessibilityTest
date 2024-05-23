package com.example.parsingtest

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo





class TestService: AccessibilityService() {
    override fun onServiceConnected() {
        Log.i("Custom","test service connected")
        super.onServiceConnected()
    }

    override fun onInterrupt() {
        Log.i("Custom","test interrupt")
    }

//    override fun onUnbind(intent: Intent?): Boolean {
//        print("test unbind")
//        return super.onUnbind(intent)
//    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        print("test event")
//        Log.i("Custom", "event=${event?.text.toString() + event?.}")
//        val nodeInfo = event!!.source ?: return

        if(event?.source != null){
            traverseAndCollectText(event.source)
        }
    }

    private fun traverseAndCollectText(node: AccessibilityNodeInfo?) {
        if (node == null) {
            return
        }
        if (node.text != null) {
            Log.i("Custom", "Retrieved text: ${node.text}")
        }
        for (i in 0 until node.childCount) {
            val childNode = node.getChild(i)
            traverseAndCollectText(childNode)
        }
    }

//    override fun onRebind(intent: Intent?) {
//        super.onRebind(intent)
//    }
}