var gulp = require('gulp');
var inject = require('gulp-inject'),
    load = require('gulp-load-plugins')(),
    path = require('path'),
    bowerFiles = require('main-bower-files'),
    es = require('event-stream'),
    angularFileSort = require('gulp-angular-filesort');

gulp.task('inject', function () {
    var target = gulp.src('index.html');
    var sources = gulp.src(bowerFiles(), {read: false});

    return target.pipe(load.inject(sources), {name: 'bower'})
                 .pipe(load.inject(es.merge(gulp.src('static/**/**/*.js', {read: false}))))
                 .pipe(gulp.dest('.'));
 });