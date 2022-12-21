//
//  NoteDetailViewModel.swift
//  iosApp
//
//  Created by eanarkulov on 12/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteDetailScreen {
    @MainActor class NoteDetalViewModel: ObservableObject {
        private var noteDataSource: NoteDataSource?
        
        private var noteId: Int64? = nil
        @Published var noteTitle = ""
        @Published var noteContent = ""
        @Published private(set) var noteColor = Note.Companion().generateRandomColor()
        
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNoteIdIfExists(id: Int64?) {
            guard let id = id else {
                return
            }

            self.noteId = id
            noteDataSource?.getNoteById(id: id) { [self] note, error in
                if error != nil {
                    print("LoadNoteIdIfExist error \(String(describing: error))")
                }
                
                guard let note = note else { return }
                
                self.noteTitle = note.title
                self.noteContent = note.content
                self.noteColor = note.colorHex
                
            }
        }
        
        func saveNote(onSaved: @escaping () -> Void) {
            if noteTitle == "" && noteContent == "" {
                onSaved()
                return
            }
            noteDataSource?.insertNote(note: Note(id: noteId == nil ? nil : KotlinLong(value: noteId!), title: self.noteTitle, content: self.noteContent, colorHex: self.noteColor, createdAt: DateTimeUtil().getCurrentDateTime())
            ) { error in
                if error != nil {
                    print("insertNote error \(String(describing: error))")
                } else {
                    onSaved()
                }
            }
        }
        
        func setParamsAndLoadNote(noteDataSource: NoteDataSource, noteId: Int64?) {
            self.noteDataSource = noteDataSource
            loadNoteIdIfExists(id: noteId)
        }
    }
}
