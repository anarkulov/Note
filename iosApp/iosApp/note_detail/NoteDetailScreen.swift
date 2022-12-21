//
//  NoteDetailScreen.swift
//  iosApp
//
//  Created by eanarkulov on 12/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteDetailScreen: View {
    private var noteDataSource: NoteDataSource
    private var noteId: Int64? = nil
    
    @StateObject var viewModel = NoteDetalViewModel(noteDataSource: nil)
    @Environment(\.presentationMode) var presentationMode
    
    init(noteDataSource: NoteDataSource, noteId: Int64? = nil) {
        self.noteDataSource = noteDataSource
        self.noteId = noteId
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField("Enter a title...", text: $viewModel.noteTitle)
            TextField("Enter a content...", text: $viewModel.noteContent)
            Spacer()
        }
        .padding()
        .background(Color(hex: viewModel.noteColor))
        .toolbar(content: {
            Button(action: {
                viewModel.saveNote {
                    self.presentationMode.wrappedValue.dismiss()
                }
            }, label: {
                Image(systemName: "checkmark")
            })
        })
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            viewModel.setParamsAndLoadNote(noteDataSource: noteDataSource, noteId: noteId)
        }
    }
}

struct NoteDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
