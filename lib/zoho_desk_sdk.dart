
import 'dart:async';

import 'package:flutter/services.dart';

class ZohoDeskSdk {
  static const MethodChannel _channel = MethodChannel('zoho_desk_sdk');
  Future<String?> init({required int orgId, required String appId, required String datacenterValue}) async{
    final String? status = await _channel.invokeMethod('initialize',{"orgId":orgId,"appId":appId,"datacenterValue":datacenterValue,},);
    return status;
  }
  Future<String?> showDashBoard() async{
    final String? status = await _channel.invokeMethod('showDashBoard',);
    return status;
  }
  Future<String?> showKnwoledgeBase() async{
    final String? status = await _channel.invokeMethod('showKnwoledgeBase',);
    return status;
  }
}
