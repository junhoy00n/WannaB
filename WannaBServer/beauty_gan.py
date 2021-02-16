import tensorflow.compat.v1 as tf
tf.disable_v2_behavior()
import numpy as np
import dlib
import matplotlib.pyplot as plt
import matplotlib.patches as patches
import numpy as np
import sys


def align_faces(img):
    dets = detector(img, 1)

    if len(dets) == 0:
        sys.exit()

    objs = dlib.full_object_detections()

    for detection in dets:
        s = sp(img, detection)
        objs.append(s)

    faces = dlib.get_face_chips(img, objs, size=256, padding=0.5)

    return faces


def preprocess(img):
    return img.astype(np.float32) / 127.5 - 1.


def postprocess(img):
    return ((img + 1.) * 127.5).astype(np.uint8)


if __name__ == '__main__':
    # Load Models

    detector = dlib.get_frontal_face_detector()
    sp = dlib.shape_predictor('models/shape_predictor_5_face_landmarks.dat')

    # Align Functionalize



    # Load BeautyGAN Pretrained

    sess = tf.Session()
    sess.run(tf.global_variables_initializer())

    saver = tf.train.import_meta_graph('models/model.meta')
    saver.restore(sess, tf.train.latest_checkpoint('models'))
    graph = tf.get_default_graph()

    X = graph.get_tensor_by_name('X:0') # source
    Y = graph.get_tensor_by_name('Y:0') # reference
    Xs = graph.get_tensor_by_name('generator/xs:0') # output

    # Preprocess and Postprocess Functions



    # Load Images

    # no makeup image(user image)
    no_makeup = dlib.load_rgb_image('media/images/ball2_encode.png')
    no_makeup_faces = align_faces(no_makeup)

    no_makeup_img = no_makeup_faces[0]

    X_img = preprocess(no_makeup_img)
    X_img = np.expand_dims(X_img, axis=0)

    # Load Images

    # office image
    office_1 = dlib.load_rgb_image('makeup/office/1.png')
    office_1_faces = align_faces(office_1)

    office_2 = dlib.load_rgb_image('makeup/office/2.png')
    office_2_faces = align_faces(office_2)

    office_3 = dlib.load_rgb_image('makeup/office/3.png')
    office_3_faces = align_faces(office_3)

    # date image
    date_1 = dlib.load_rgb_image('makeup/date/1.png')
    date_1_faces = align_faces(date_1)

    date_2 = dlib.load_rgb_image('makeup/date/2.png')
    date_2_faces = align_faces(date_2)

    date_3 = dlib.load_rgb_image('makeup/date/3.png')
    date_3_faces = align_faces(date_3)

    date_4 = dlib.load_rgb_image('makeup/date/4.png')
    date_4_faces = align_faces(date_4)

    # freshman image
    freshman_1 = dlib.load_rgb_image('makeup/freshman/1.png')
    freshman_1_faces = align_faces(freshman_1)

    freshman_2 = dlib.load_rgb_image('makeup/freshman/2.png')
    freshman_2_faces = align_faces(freshman_2)

    freshman_3 = dlib.load_rgb_image('makeup/freshman/3.png')
    freshman_3_faces = align_faces(freshman_3)

    # guest image
    guest_1 = dlib.load_rgb_image('makeup/guest/1.png')
    guest_1_faces = align_faces(guest_1)

    guest_2 = dlib.load_rgb_image('makeup/guest/2.png')
    guest_2_faces = align_faces(guest_2)

    guest_3 = dlib.load_rgb_image('makeup/guest/3.png')
    guest_3_faces = align_faces(guest_3)

    # profile image
    profile_1 = dlib.load_rgb_image('makeup/profile/1.png')
    profile_1_faces = align_faces(profile_1)

    profile_2 = dlib.load_rgb_image('makeup/profile/2.png')
    profile_2_faces = align_faces(profile_2)

    profile_3 = dlib.load_rgb_image('makeup/profile/3.png')
    profile_3_faces = align_faces(profile_3)

    profile_4 = dlib.load_rgb_image('makeup/profile/4.png')
    profile_4_faces = align_faces(profile_4)

    # ggu image
    ggu_1 = dlib.load_rgb_image('makeup/ggu/1.png')
    ggu_1_faces = align_faces(ggu_1)

    ggu_2 = dlib.load_rgb_image('makeup/ggu/2.png')
    ggu_2_faces = align_faces(ggu_2)

    ggu_3 = dlib.load_rgb_image('makeup/ggu/3.png')
    ggu_3_faces = align_faces(ggu_3)

    # office image Run

    office_1_img = office_1_faces[0]

    Y_img = preprocess(office_1_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/office/1.png', postprocess(output[0]))
        
    office_2_img = office_2_faces[0]

    Y_img = preprocess(office_2_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/office/2.png', postprocess(output[0]))

    office_3_img = office_3_faces[0]

    Y_img = preprocess(office_3_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/office/3.png', postprocess(output[0]))

    # date image Run

    date_1_img = date_1_faces[0]

    Y_img = preprocess(date_1_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/date/1.png', postprocess(output[0]))
        
    date_2_img = date_2_faces[0]

    Y_img = preprocess(date_2_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/date/2.png', postprocess(output[0]))

    date_3_img = date_3_faces[0]

    Y_img = preprocess(date_3_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/date/3.png', postprocess(output[0]))
        
    date_4_img = date_4_faces[0]

    Y_img = preprocess(date_4_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/date/4.png', postprocess(output[0]))

    # freshman image Run

    freshman_1_img = freshman_1_faces[0]

    Y_img = preprocess(freshman_1_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/freshman/1.png', postprocess(output[0]))
        
    freshman_2_img = freshman_2_faces[0]

    Y_img = preprocess(freshman_2_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/freshman/2.png', postprocess(output[0]))

    freshman_3_img = freshman_3_faces[0]

    Y_img = preprocess(freshman_3_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/freshman/3.png', postprocess(output[0]))

    # guest image Run

    guest_1_img = guest_1_faces[0]

    Y_img = preprocess(guest_1_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/guest/1.png', postprocess(output[0]))
        
    guest_2_img = guest_2_faces[0]

    Y_img = preprocess(guest_2_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/guest/2.png', postprocess(output[0]))

    guest_3_img = guest_3_faces[0]

    Y_img = preprocess(guest_3_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/guest/3.png', postprocess(output[0]))

    # profile image Run

    profile_1_img = profile_1_faces[0]

    Y_img = preprocess(profile_1_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/profile/1.png', postprocess(output[0]))
        
    profile_2_img = profile_2_faces[0]

    Y_img = preprocess(profile_2_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/profile/2.png', postprocess(output[0]))

    profile_3_img = profile_3_faces[0]

    Y_img = preprocess(profile_3_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/profile/3.png', postprocess(output[0]))
        
    profile_4_img = profile_4_faces[0]

    Y_img = preprocess(profile_4_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/profile/4.png', postprocess(output[0]))

    # ggu image Run

    ggu_1_img = ggu_1_faces[0]

    Y_img = preprocess(ggu_1_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/ggu/1.png', postprocess(output[0]))
        
    ggu_2_img = ggu_2_faces[0]

    Y_img = preprocess(ggu_2_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/ggu/2.png', postprocess(output[0]))

    ggu_3_img = ggu_3_faces[0]

    Y_img = preprocess(ggu_3_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/ggu/3.png', postprocess(output[0]))