package moe.henry_zhr.telegram_font

import android.content.res.AssetManager
import android.graphics.Typeface
import androidx.annotation.Keep
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

@Keep
class MainHook : IXposedHookLoadPackage {
  override fun handleLoadPackage(lpparam: LoadPackageParam) {
    if (lpparam.packageName != TELEGRAM_PACKAGE_NAME)
      return
    XposedHelpers.findAndHookConstructor(
      "android.graphics.Typeface.Builder",
      lpparam.classLoader,
      AssetManager::class.java,
      String::class.java,
      object : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam) {
          when (param.args[1]) {
            "fonts/rmediumitalic.ttf" -> param.result =
              Typeface.Builder("/system/fonts/CodeNewRomanItalicNerdFontCompleteMono.otf")
            "fonts/rmedium.ttf" -> param.result =
              Typeface.Builder("/system/fonts/CodeNewRomanBoldNerdFontCompleteMono.otf")
            "fonts/ritalic.ttf" -> param.result =
              Typeface.Builder("/system/fonts/CodeNewRomanItalicNerdFontCompleteMono.otf")
          }
        }
      })
  }

  companion object {
    private const val TELEGRAM_PACKAGE_NAME = "org.telegram.messenger"
  }
}