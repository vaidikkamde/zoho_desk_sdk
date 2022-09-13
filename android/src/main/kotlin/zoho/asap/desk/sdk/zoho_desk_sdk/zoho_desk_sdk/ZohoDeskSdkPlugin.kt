package zoho.asap.desk.sdk.zoho_desk_sdk.zoho_desk_sdk

import androidx.annotation.NonNull
import com.zoho.desk.asap.api.ZohoDeskPortalSDK
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import android.app.Application;
/** ZohoDeskSdkPlugin */
class ZohoDeskSdkPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var context: Context
  private lateinit var activity: Activity
  private lateinit var apiProvider: ZohoDeskPortalSDK
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "zoho_desk_sdk")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext

    ZohoDeskPortalSDK.Logger.enableLogs();
    apiProvider = ZohoDeskPortalSDK.getInstance(getApplicationContext());
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "initialize") {
      val map = call.arguments as HashMap<String, Any>
      val dataCenterString = map["datacenterValue"].toString();
      val dataCenterValue = if(dataCenterString == "CN") DataCenter.CN else if(dataCenterString == "EU") DataCenter.EU else if(dataCenterString == "US") DataCenter.US else if(dataCenterString == "IN") DataCenter.IN else if(dataCenterString == "AU") DataCenter.AU else if(dataCenterString == "JP") DataCenter.JP else DataCenter.US;
      apiProvider.initDesk(map["orgId"].toLong(),map["appId"].toString(),dataCenterValue);
      result.success("Android Crisp sdk initialized successful");
    } else if (call.method == "showDashBoard"){
      ZDPortalHome.show(activity);
      result.success("Show Dashboard successful");
    } else if (call.method == "showChat"){
      val chatUser = ZDPortalChatUser()
      val map = call.arguments as HashMap<String, Any>
      chatUser.setName(map["name"].toString())
      chatUser.setEmail(map["email"].toString())
      chatUser.setPhone(map["phone"].toString())
      ZDPortalChat.setGuestUserDetails(chatUser)
      ZDPortalChat.show(activity);
      result.success("Show Dashboard successful");
    } else if (call.method == "showKnwoledgeBase"){
      ZDPortalKB.show(activity);
      result.success("Show Knwoledge Base successful");
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity;
  }

  override fun onDetachedFromActivityForConfigChanges() {

  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

  }

  override fun onDetachedFromActivity() {

  }
}
