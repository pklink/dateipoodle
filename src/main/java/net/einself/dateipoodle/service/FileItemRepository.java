package net.einself.dateipoodle.service;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import net.einself.dateipoodle.domain.FileItem;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class FileItemRepository implements PanacheRepositoryBase<FileItem, String> { }
