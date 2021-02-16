
from django.http import JsonResponse, HttpResponse

import json
import os

import base64

import cv2


LAB_IP = "192.9.44.148:1111"
DORMITORY_IP = "192.168.0.8:1111"
ORMAK_IP = "172.30.1.13:1111"

now_id = "0"

def makeup(request):


    theme = request.POST["theme"]
    code = request.POST["code"]
    id = request.POST["id"]

    code = base64.decodestring(code.encode())
    image_result = open('media/images/ball2_encode.PNG', 'wb')
    image_result.write(code)

    global now_id
    if id != now_id:
        print("======================")
        print()
        print()
        print("RUN")
        print()
        print()
        print("======================")
        now_id = id
        os.system("python beauty_gan.py")

    if theme == "신입생":
        result = [
            {"id": 1, "code": base64.b64encode(open("media/freshman/1.png", "rb").read()).decode("UTF-8")},
            {"id": 2, "code": base64.b64encode(open("media/freshman/2.png", "rb").read()).decode("UTF-8")},
            {"id": 3, "code": base64.b64encode(open("media/freshman/3.png", "rb").read()).decode("UTF-8")},
        ]
        return HttpResponse(json.dumps(result, ensure_ascii=True))


    elif theme == "데이트":
        result = [
            {"id": 6, "code": base64.b64encode(open("media/date/1.png", "rb").read()).decode("UTF-8")},
            {"id": 7, "code": base64.b64encode(open("media/date/2.png", "rb").read()).decode("UTF-8")},
            {"id": 8, "code": base64.b64encode(open("media/date/3.png", "rb").read()).decode("UTF-8")},
            {"id": 9, "code": base64.b64encode(open("media/date/4.png", "rb").read()).decode("UTF-8")},

        ]
        return HttpResponse(json.dumps(result, ensure_ascii=True))

    elif theme == "하객":
        result = [
            {"id": 11, "code": base64.b64encode(open("media/guest/1.png", "rb").read()).decode("UTF-8")},
            {"id": 12, "code": base64.b64encode(open("media/guest/2.png", "rb").read()).decode("UTF-8")},
            {"id": 13, "code": base64.b64encode(open("media/guest/3.png", "rb").read()).decode("UTF-8")}
        ]
        return HttpResponse(json.dumps(result, ensure_ascii=True))

    elif theme == "증명사진":
        result = [
            {"id": 16, "code": base64.b64encode(open("media/profile/1.png", "rb").read()).decode("UTF-8")},
            {"id": 17, "code": base64.b64encode(open("media/profile/2.png", "rb").read()).decode("UTF-8")},
             {"id": 18, "code": base64.b64encode(open("media/profile/3.png", "rb").read()).decode("UTF-8")},
            {"id": 19, "code": base64.b64encode(open("media/profile/4.png", "rb").read()).decode("UTF-8")}
        ]
        return HttpResponse(json.dumps(result, ensure_ascii=True))

    elif theme == "꾸안꾸":
        result = [
            {"id": 21, "code": base64.b64encode(open("media/ggu/1.png", "rb").read()).decode("UTF-8")},
            {"id": 22, "code": base64.b64encode(open("media/ggu/2.png", "rb").read()).decode("UTF-8")},
            {"id": 23, "code": base64.b64encode(open("media/ggu/3.png", "rb").read()).decode("UTF-8")},

        ]
        return HttpResponse(json.dumps(result, ensure_ascii=True))

    elif theme == "회사":
        result = [
            {"id": 26, "code": base64.b64encode(open("media/office/1.png", "rb").read()).decode("UTF-8")},
            {"id": 27, "code": base64.b64encode(open("media/office/2.png", "rb").read()).decode("UTF-8")},
            {"id": 28, "code": base64.b64encode(open("media/office/3.png", "rb").read()).decode("UTF-8")}

        ]
        return HttpResponse(json.dumps(result, ensure_ascii=True))

    makeup_id = request.POST["id"]
    s = str(makeup_id)

    path_dir = "media/cosmetic/s"

    file_list = os.listdir(path_dir)

    img_name = path_dir + '/' + file_list
    img = cv2.imread(img_name, cv2.IMREAD_COLOR)
    cv2.imshow('image', img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    return HttpResponse(json.dumps(img, ensure_ascii=True))


def sum(request):
    noMakeupCode = request.POST["no_makeup"]
    noMakeupCode = base64.decodestring(noMakeupCode.encode())
    write1 = open('media/images/ball2_encode.png', 'wb')
    write1.write(noMakeupCode)

    makeupCode = request.POST["makeup"]
    makeupCode = base64.decodestring(makeupCode.encode())
    write2 = open('media/images/ball1_encode.png', 'wb')
    write2.write(makeupCode)

    os.system("python beauty_gan_custom.py")
    return HttpResponse(base64.b64encode(open("media/custom/custom.png", "rb").read()).decode("UTF-8"))


def information(request):
    makeup_id = request.POST["id"]
    path_dir = "media/cosmetic/" + makeup_id

    file_list = os.listdir(path_dir)
    cosmetics = []
    for name in file_list:
        realPath = path_dir + "/" + name
        code = base64.b64encode(open(realPath, "rb").read()).decode("UTF-8")

        onlyName = name[0:name.find(".")]
        try:
            f = open(path_dir + "/" + onlyName + ".txt", "r")
            cosmetics.append({"link": f.readline(), "code": code})
        except Exception as e:
            print("No File : " + path_dir + "/" + onlyName + ".txt")
            cosmetics.append({"link": "http://www.gmarket.co.kr", "code": code})


    result = {
        "link": "https://www.youtube.com/watch?v=ISx7VDIJdSA",
        "cosmetics": cosmetics
    }
    return HttpResponse(json.dumps(result, ensure_ascii=True))